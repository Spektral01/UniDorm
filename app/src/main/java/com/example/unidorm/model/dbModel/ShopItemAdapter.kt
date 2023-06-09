import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unidorm.R
import com.example.unidorm.model.dbModel.ShopItemModel
import com.google.firebase.storage.FirebaseStorage

class ShopItemAdapter(private val itemList: MutableList<ShopItemModel>, private val onItemClick: (ShopItemModel) -> Unit) :
    RecyclerView.Adapter<ShopItemAdapter.ViewHolder>() {

    private val originalItemList: MutableList<ShopItemModel> = mutableListOf()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemText: TextView = itemView.findViewById(R.id.textView3)
        val itemImage: ImageView = itemView.findViewById(R.id.imageViewItemList)
        init {
            originalItemList.addAll(itemList)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]

                    onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shop_list, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.itemText.text = item.Item

        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("images/" + item.Picture + ".jpg")

        imageRef.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()
            // Загрузка изображения из Firebase Storage с помощью библиотеки Glide
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .into(holder.itemImage)
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Error getting download URL: ${exception.message}")
        }


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun clear() {
        itemList.clear()
        notifyDataSetChanged()
    }


}
