package minhtvph26873.poly.pnlib.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.adapter.Top10SachAdapter;
import minhtvph26873.poly.pnlib.dao.ThongKeDAO;
import minhtvph26873.poly.pnlib.model.Top;

import java.util.List;


public class Top10Fragment extends Fragment {


    public Top10Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }

    private List<Top> topList;
    private Top10SachAdapter adapter;
    private ThongKeDAO thongKeDAO;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewtop10);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        thongKeDAO = new ThongKeDAO(getContext());
        adapter = new Top10SachAdapter(getContext());
        topList = thongKeDAO.getTop();
        adapter.setData(topList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        topList = thongKeDAO.getTop();
        adapter.setData(topList);
    }
}