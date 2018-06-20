package com.mojec.mojecsmarthome.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mojec.mojecsmarthome.R;
import com.mojec.mojecsmarthome.activity.PostsAdapter;
import com.mojec.mojecsmarthome.databinding.FragmentProfileBinding;
import com.mojec.mojecsmarthome.databinding.model.Post;
import com.mojec.mojecsmarthome.databinding.model.User;
import com.mojec.mojecsmarthome.databinding.utils.GridSpacingItemDecoration;

import java.util.ArrayList;


public class ProfileFragment extends Fragment implements PostsAdapter.PostsAdapterListener {

    private ProfileFragment.MyClickHandlers handlers;
    private PostsAdapter mAdapter;
    private RecyclerView recyclerView;
    private FragmentProfileBinding binding;
    private User user;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile, container, false);

        View view = binding.getRoot();

        handlers = new ProfileFragment.MyClickHandlers(getActivity());

//        initRecyclerView();
        renderProfile();
        return view;
    }

    /**
     * Renders RecyclerView with Grid Images in 3 columns
     */
    /*private void initRecyclerView() {
        recyclerView = binding.content.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(4), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new PostsAdapter(getPosts(), this);
        recyclerView.setAdapter(mAdapter);
    }*/

    /**
     * Renders user profile data
     */
    private void renderProfile() {
        user = new User();
        user.setName("Vivian Omelime");
        user.setEmail("vivian@mojec.com");
        user.setProfileImage("https://image.ibb.co/k3dUvy/Vivian.png");
        user.setAbout("Vivian's Home");

        // ObservableField doesn't have setter method, instead will
        // be called using set() method
        user.numberOfPosts.set("ON");
        user.numberOfFollowers.set(5L);
        user.numberOfFollowing.set(7L);


        // display user
        binding.setUser(user);

        // assign click handlers
        binding.content.setHandlers(handlers);
    }

    private ArrayList<Post> getPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Post post = new Post();
            post.setImageUrl("https://api.androidhive.info/images/nature/" + i + ".jpg");

            posts.add(post);
        }

        return posts;
    }

    @Override
    public void onPostClicked(Post post) {
        Toast.makeText(getContext(), "Post clicked! " + post.getImageUrl(), Toast.LENGTH_SHORT).show();
    }

    public class MyClickHandlers {

        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }

        /**
         * Demonstrating updating bind data
         * Profile name, number of posts and profile image
         * will be updated on Fab click
         */
        public void onProfileFabClicked(View view) {
            user.setName("Sir David Attenborough");
            user.setProfileImage("https://api.androidhive.info/images/nature/david1.jpg");

            // updating ObservableField
            user.numberOfPosts.set("ON");
            user.numberOfFollowers.set(5L);
            user.numberOfFollowing.set(7L);
        }

        public boolean onProfileImageLongPressed(View view) {
            Toast.makeText(getContext(), "Profile image long pressed!", Toast.LENGTH_LONG).show();
            return false;
        }


        public void onFollowersClicked(View view) {
            Toast.makeText(context, "Followers is clicked!", Toast.LENGTH_SHORT).show();
        }

        public void onFollowingClicked(View view) {
            Toast.makeText(context, "Following is clicked!", Toast.LENGTH_SHORT).show();
        }

        public void onPostsClicked(View view) {
            Toast.makeText(context, "Posts is clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
