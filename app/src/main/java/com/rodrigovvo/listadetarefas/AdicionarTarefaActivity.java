package com.rodrigovvo.listadetarefas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rodrigovvo.listadetarefas.Model.Tarefa;
import com.rodrigovvo.listadetarefas.helper.TarefaDAO;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.textTarefa);

        // RECUPERAR TAREFA, CASO SEJA EDIÇÃO
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        // CONFIGURAR A TAREFA NA CAIXA DE TEXTO
        if (tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.botaoSalvar:
                Log.i("Botao", "clicou no botão salvar");
                //EXECUTA AÇÃO PARA O ITEM SALVAR
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if (tarefaAtual != null){  // -- EDIÇÃO  --- NO CASO DE EDIÇÃO = OU SEJA, QUANDO A TAREFA ATUAL JÁ CONTER ELEMENTOS DIGITADOS
                    // OU SEJA QUANDO A TAREFA ATUAL FOI DIFERENTE DE NULO.

                    String textoDigitadoPeloUsuario = editTarefa.getText().toString();
                    if (!textoDigitadoPeloUsuario.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa( textoDigitadoPeloUsuario);
                        tarefa.setId(tarefaAtual.getId());
                        if(tarefaDAO.atualizar( tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao atualiza a tarefa!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro! Não foi possível atualizar a tarefa.", Toast.LENGTH_SHORT).show();
                        }

                    }

                }else { // --- SALVA --- UTILIZADA NA PRIMEIRA VEZ QUE FOR CRIADA OU QUANDO NÃO HOUVE TEXTO, VAI ADICIONAR
                    // UMA NOVA HOLDE  NO RECYCLERVIEW

                    String textoDigitadoPeloUsuario = editTarefa.getText().toString();
                    if (!textoDigitadoPeloUsuario.isEmpty()){

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(textoDigitadoPeloUsuario);
                        if (tarefaDAO.salvar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao salvar a tarefa!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro! Não foi possível salvar a tarefa.", Toast.LENGTH_SHORT).show();
                        }


                    }

                }

            break;
        }

        return super.onOptionsItemSelected(item);
    }
}
