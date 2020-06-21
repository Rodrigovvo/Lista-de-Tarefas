package com.rodrigovvo.listadetarefas.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodrigovvo.listadetarefas.Model.Tarefa;
import com.rodrigovvo.listadetarefas.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    private List<Tarefa> listaTarefas;

    public Adapter(List<Tarefa> lista) {

        this.listaTarefas = lista;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.lista_tarefa_adapter, parent, false);

        return new  myViewHolder(itemLista);
    }

    @Override
    public int getItemCount() {
        return this.listaTarefas.size();
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        Tarefa tarefa = listaTarefas.get(position);
        holder.tarefa.setText( tarefa.getNomeTarefa());



    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView tarefa;

    public myViewHolder(@NonNull View itemView) {
        super(itemView);

        tarefa = itemView.findViewById(R.id.textTarefa);
    }
}

}
