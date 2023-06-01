package com.example.unidorm.model.dbModel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AdapterShopItem(context: Context, resource: Int, objects: List<ShopItemModel>) :
    ArrayAdapter<ShopItemModel>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
        }

        val item = getItem(position)
        val textView1 = itemView?.findViewById<TextView>(android.R.id.text1)
        val textView2 = itemView?.findViewById<TextView>(android.R.id.text2)

        textView1?.text = item?.Item
        textView2?.text = item?.Price

        return itemView!!
    }
}