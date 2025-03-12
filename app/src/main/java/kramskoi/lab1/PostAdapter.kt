package kramskoi.lab1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)

        holder.itemView.findViewById<ImageButton>(R.id.btn_like).setOnClickListener {
            post.likes++
            holder.itemView.findViewById<TextView>(R.id.post_likes).text =
                holder.itemView.context.getString(R.string.likes_format, post.likes)
            Toast.makeText(holder.itemView.context, "Liked!", Toast.LENGTH_SHORT).show()
        }

        holder.itemView.findViewById<ImageButton>(R.id.btn_comment).setOnClickListener {
            Toast.makeText(holder.itemView.context, "Comment clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            val textView = itemView.findViewById<TextView>(R.id.post_text)
            val imageView = itemView.findViewById<ImageView>(R.id.post_image)
            val likesView = itemView.findViewById<TextView>(R.id.post_likes)
            val commentsView = itemView.findViewById<TextView>(R.id.post_comments)

            textView.text = post.text
            likesView.text = itemView.context.getString(R.string.likes_format, post.likes)
            commentsView.text = itemView.context.getString(R.string.comments_format, post.comments)

            if (post.imageUrl != null) {
                Glide.with(itemView.context)
                    .load(post.imageUrl)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_error)
                    .into(imageView)
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.GONE
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

data class Post(
    val id: Int,
    val text: String,
    val imageUrl: String?,
    var likes: Int,
    val comments: Int
)
