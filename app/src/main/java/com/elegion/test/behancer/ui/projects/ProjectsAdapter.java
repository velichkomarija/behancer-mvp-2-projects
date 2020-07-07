package com.elegion.test.behancer.ui.projects;

import android.annotation.SuppressLint;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.elegion.test.behancer.data.model.project.RichProject;
import com.elegion.test.behancer.databinding.ProjectBinding;
import com.elegion.test.behancer.ui.projects.commonprojectsui.ProjectsHolder;

public class ProjectsAdapter extends PagedListAdapter<RichProject, ProjectsHolder> {

    private static final DiffUtil.ItemCallback<RichProject> CALLBACK = new DiffUtil.ItemCallback<RichProject>() {
        @Override
        public boolean areItemsTheSame(RichProject oldItem, RichProject newItem) {
            return oldItem.mProject.getId() == newItem.mProject.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(RichProject oldItem, RichProject newItem) {
            return oldItem.equals(newItem);
        }
    };
    private final OnItemClickListener mOnItemClickListener;

    public ProjectsAdapter() {
        super(CALLBACK);
        mOnItemClickListener = null;
    }

    public ProjectsAdapter(OnItemClickListener onItemClickListener) {
        super(CALLBACK);
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ProjectBinding binding = ProjectBinding.inflate(inflater, parent, false);
        return new ProjectsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsHolder holder, int position) {
        RichProject project = getItem(position);
        if (project != null) {
            holder.bind(project, mOnItemClickListener);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String username);
    }
}
