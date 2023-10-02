package com.example.wordsapp


import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding

class LetterListFragment : Fragment() {
    // getting reference to FragmentLetterListBinding
    private var _binding: FragmentLetterListBinding? = null

    private val binding get() = _binding!!

    // property for recyclerView
    private lateinit var recyclerView: RecyclerView

    private var isLinearLayoutManager = true;

    // display options menu in onCreate() by calling setHasOptionsMenu(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     * Note: in Fragments, the layout is inflated in onCreateView()
     *
     * inflating the view
     * setting the value of _binding, and
     * returning the root view
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        chooseLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)
    }

    private fun chooseLayout(){
        /**
         * here you assign the layout-manager. In addition to setting the
         * layout-manager, this code also assigns the adapter. LetterAdapter is used for both list and
         * grid layouts
         * */
        when(isLinearLayoutManager){
            true -> {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = LetterAdapter()
            }
            false -> {
                recyclerView.layoutManager = GridLayoutManager(context, 4)
                recyclerView.adapter = LetterAdapter()
            }
        }
    }

    private fun setIcon(menuItem: MenuItem?){
        if(menuItem == null){
            return;
        }
        // the icon is conditionally set based on the isLinearLayoutManager property
        menuItem.icon  =
            if(isLinearLayoutManager){
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
            } else{
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_linear_layout)
            }
    }

    /**
     * this is called any time a menu item is tapped so you need to be sure to check which menu
     * item is tapped. You use a when statement, above. If the id matches the action the
     * action_switch_layout menu item, you negate the value of the isLinearLayoutManager. Then
     * call chooseLayout() and setIcon() to update the UI accordingly.
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_switch_layout -> {
                // Sets isLinearLayoutManager (a Boolean) to the opposite value
                isLinearLayoutManager = !isLinearLayoutManager
                //Sets layout and icon
                chooseLayout()
                setIcon(item)
                return true
            }
            /**
             * otherwise, do nothing and use the core event handling
             *
             * when clauses require that all possible paths be accounted for explicitly,
             * for instance both the true and false cases if the value is Boolean,
             * or an else to catch all the unhandled cases
             * */
            else -> super.onOptionsItemSelected(item)
        }
    }



}