package com.example.exemplofirebase;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    // Nathalia Calza
    // Ana Luísa Crecca
    // Atributo: referência para o nosso banco de dados!
    // Esta referência "aponta" para o nó RAIZ da árvore!
    // Atributos que representam os objetos da interface gráfica:
    private EditText txtMesa;
    private EditText txtItem;
    private EditText txtProduto;
    private Button btnAdicionar;
    private ListView listaProdutos;

    private DatabaseReference BD = FirebaseDatabase.getInstance().getReference();

    private ProdutosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ligando os atributos com os objetos na interface gráfica:
        txtMesa = findViewById( R.id.txtMesa );
        txtItem = findViewById( R.id.txtItem );
        txtProduto = findViewById( R.id.txtProduto );
        btnAdicionar = findViewById( R.id.btnInserir );
        listaProdutos = findViewById( R.id.lista );

        // Criando e associando o escutador de cliques do botão "adicionar":
        btnAdicionar.setOnClickListener( new EscutadorBotaoAdicionar() );

        // Referência para o nó principal deste exemplo:
        DatabaseReference cont26 = BD.child( "exercicioRestaurante" );

        // Criando objeto com parâmetros para o adapter:
        FirebaseListOptions<Produto> opt = new FirebaseListOptions.Builder<Produto>().setLayout(R.layout.item_lista).setQuery(cont26, Produto.class).setLifecycleOwner(this).build();

        // Criando o objeto adapter (usando os parâmetros criados acima):
        adapter = new ProdutosAdapter(opt);

        // Colocando o adapter no ListView:
        listaProdutos.setAdapter(adapter);

//      Criando e associando o escutador de cliques na lista:
        listaProdutos.setOnItemClickListener(new EscutadorCliqueLista());

        listaProdutos.setOnItemLongClickListener(new EscutadorCliqueLista());

    }
    private class ProdutosAdapter extends FirebaseListAdapter<Produto> {
        public ProdutosAdapter(FirebaseListOptions options) {
            super(options);
        }

        // Método que coloca dados ("povoa") a View (o desenho) do item da lista.
        // Recebe o objeto com os dados (vindos do Firebase), e a View já inflada.
        // Basta acessarmos os dados (nome e email) e colocarmos nos objetos corretos
        // dentro da View.
        @Override
        protected void populateView(View v, Produto p, int position) {

            // Acessa os objetos gráficos dentro do desenho do item da lista:
            TextView lblMesa = v.findViewById(R.id.lblMesa);
            TextView lblItem = v.findViewById(R.id.lblItem);
            TextView lblProduto = v.findViewById(R.id.lblProduto);
            TextView lblAtendido = v.findViewById(R.id.lblAtendido);

            // Coloca dados do objeto Contato (c) nesses objetos gráficos:
            lblMesa.setText(p.getMesa());
            lblItem.setText(p.getItem());
            lblProduto.setText(p.getProduto());
            if (p.isAtendido()) {
                lblAtendido.setText("SIM");
            } else {
                lblAtendido.setText("NÃO");
            }
        }
    }


    private class EscutadorBotaoAdicionar implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            // Variáveis auxiliares:
            String mesa, item, produto, id;

            // Referência para o nó principal deste exemplo:
            DatabaseReference restaurante = BD.child("exercicioRestaurante");

            // Pegando os dados digitados nas caixas.
            //mesa
            mesa = txtMesa.getText().toString();

            //item
            item = txtItem.getText().toString();

            //produto
            produto = txtProduto.getText().toString();

            // Gerando um nó aleatório, que será utilizado como "chave" para
            // os dados deste usuário (como se fosse a "chave" do registro na tabela.
            // OBS:  - método push() :   gera o valor aleatório.
            //       - método getKey() : devolve o valor gerado, pra podermos usar.
            id = restaurante.push().getKey();

            // Criando uma intancia de produto
            Produto prod = new Produto(id, mesa, item, produto);

            // Enfim, gravando os dados deste usuário "debaixo" deste nó gerado:
            restaurante.child(id).setValue(prod);

            //limpando campos
            txtMesa.setText("");
            txtItem.setText("");
            txtProduto.setText("");

        }
    }

    // Classe interna do escutador de clicks na lista:

    private class EscutadorCliqueLista implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        DatabaseReference restaurante = BD.child("exercicioRestaurante");
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            // Recuperando o objeto que está na posição "i" da lista:
            Produto item = adapter.getItem(i);

            // Alterado atendido
            item.setAtendido(true);

            // Gravando firebase
            restaurante.child(item.getChave()).setValue(item);
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            // Recuperando o objeto que está na posição "i" da lista:
            Produto item = adapter.getItem(i);

            // Excluindo firebase
            restaurante.child(item.getChave()).setValue(null);
            return false;
        }
    }
}