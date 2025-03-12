package kramskoi.lab1

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MainActivity : ComponentActivity() {

    private lateinit var adapter: PostAdapter
    private val posts = mutableListOf<Post>()
    private var currentPostIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val avatarUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTswQY-YLQ5IjnijS5I_KQa53OdoarB-Mkptw&s"
        val avatarImageView = findViewById<ImageView>(R.id.avatar)
        Glide.with(this)
            .load(avatarUrl)
            .placeholder(R.drawable.ic_avatar_placeholder)
            .error(R.drawable.ic_error)
            .into(avatarImageView)

        val postsRecyclerView = findViewById<RecyclerView>(R.id.posts_recycler_view)
        postsRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PostAdapter()
        postsRecyclerView.adapter = adapter

        addMorePosts()

        postsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition == totalItemCount - 1) {
                    addMorePosts()
                }
            }
        })

        findViewById<Button>(R.id.btn_follow).setOnClickListener {
            Toast.makeText(this, "You have subscribed", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btn_message).setOnClickListener {
            Toast.makeText(this, "The user has disabled receiving messages", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addMorePosts() {
        val newPosts = listOf(
            Post(
                id = 1,
                text = "hehe",
                imageUrl = "https://img-webcalypt.ru/storage/memes/34316/20253/L9BKphL42hx4iFOHQIIYCRy3rKcv6CkUrIUTaOqSnxOmBFsA4rYiSzVoylU5FdvshNZCLCOXsOxQQmRv1EwVYCtxrsotijtDvJWUhCBPabAI6AxeKdAEqPWxpYemm9N3-md.jpeg",
                likes = 10,
                comments = 5
            ),
            Post(
                id = 2,
                text = "haha",
                imageUrl = "https://cs13.pikabu.ru/post_img/big/2021/02/05/6/1612512207143356025.jpg",
                likes = 15,
                comments = 3
            ),
            Post(
                id = 3,
                text = "idk",
                imageUrl = "https://static-cdn.jtvnw.net/jtv_user_pictures/8997d1f1-a3c6-4956-841c-923499efb0f5-profile_image-300x300.png",
                likes = 20,
                comments = 8
            )
        )

        posts.addAll(newPosts)
        adapter.submitList(posts.toList())
        currentPostIndex += 3
    }
}