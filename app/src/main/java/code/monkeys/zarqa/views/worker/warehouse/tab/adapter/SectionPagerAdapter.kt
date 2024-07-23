package code.monkeys.zarqa.views.worker.warehouse.tab.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import code.monkeys.zarqa.views.worker.warehouse.tab.allproduct.AllProductFragment
import code.monkeys.zarqa.views.worker.warehouse.tab.deleteproduct.DeleteProductFragment
import code.monkeys.zarqa.views.worker.warehouse.tab.lowstockproduct.LowStockProductFragment

class SectionPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = AllProductFragment()
            1 -> fragment = LowStockProductFragment()
            2 -> fragment = DeleteProductFragment()
        }
        return fragment as Fragment
    }
}