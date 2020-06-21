package com.rodrigovvo.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rodrigovvo.listadetarefas.Model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements iTarefaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;



    public TarefaDAO(Context context ) // EU ESTOU EXIGINDO O CONTEXTO PQ O VOU INSTANCIAR O DBHELPER
    // NESSE CASO, POSSO VERIFICAR QUE O CONSTRUTOR DO DBHELPER EXIGE O CONTEXTO, PORTANTO, QUANDO EU
    // FOR INSTANCIAR DO DBHELPER VOU TER RECEBIDO O CONTEXTO COMO PARÂMETRO E VOU REPASSAR A CLASSE QUE
    // ESTOU INSTANCIANDO. - NO CASO EU RECEBI O CONTEXTO DA 'ADICIONARTAREFAACTIVITY" NA PARTE DE "onOptionsItemSelected"
    // QUANDO O BOTÃO DE SALVAR FOI SELECIONADO NO MENU
    {
        DBHelper db = new DBHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
            }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());


        try {
            escreve.insert(DBHelper.TABELA_TAREFAS, null, cv);
            Log.i("INFO", "Tarefa salva com suscesso");
        }catch (Exception e){
            Log.i("INFO", "Erro ao salvar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());


        try {
            String[] args = {tarefa.getId().toString()};
            escreve.update(DBHelper.TABELA_TAREFAS, cv, "id=?", args );
            Log.i("INFO", "Tarefa atualizada com suscesso");
        }catch (Exception e){
            Log.i("INFO", "Erro ao atualizar tarefa " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = { tarefa.getId().toString()};
           escreve.delete(DBHelper.TABELA_TAREFAS, "id=?", args );
            Log.i("INFO", "Tarefa removida com suscesso");
        }catch (Exception e){
            Log.i("INFO", "Erro ao remover tarefa " + e.getMessage());
            return false;
        }


        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new  ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";

        Cursor c = le.rawQuery(sql, null);


        while ( c.moveToNext() ){

            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));


            tarefa.setId(id );
            tarefa.setNomeTarefa( nomeTarefa);

            tarefas.add(tarefa);

        }

        return tarefas;
    }
}
