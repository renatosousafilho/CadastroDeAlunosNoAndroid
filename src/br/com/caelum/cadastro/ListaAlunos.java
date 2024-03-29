package br.com.caelum.cadastro;

import java.util.List;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.modelo.Aluno;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAlunos extends Activity {
	ListView listaAlunos;
	private List<Aluno> alunos;
	private AlunoDAO alunoDAO;
	private Aluno alunoSelecionado;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_alunos);
		
		 alunoDAO = new AlunoDAO(this);
		listaAlunos = (ListView) findViewById(R.id.lista_alunos);
	
		registerForContextMenu(listaAlunos);
		
		listaAlunos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int posicao,
					long id) {
				Toast.makeText(ListaAlunos.this, "Posição selecionada:"+ posicao, Toast.LENGTH_LONG).show();
				
			}
		});
		
		listaAlunos.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View arg1,
					int posicao, long id) {
				alunoSelecionado = alunos.get(posicao);
				return false;
			}
		});
		
		

	}
	
	private void recarregarLista(){
		alunos = alunoDAO.lista();
		alunoDAO.close();
		ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
		listaAlunos.setAdapter(adapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		recarregarLista();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int idSelecionado = item.getItemId();
		switch (idSelecionado) {
		case R.id.menu_novo:
			Intent intent = new Intent(this, FormularioActivity.class);
			startActivity(intent);
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add("Ligar");
		MenuItem editar = menu.add("Editar");
		editar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Intent irParaFormulario = new Intent(ListaAlunos.this, FormularioActivity.class);
				irParaFormulario.putExtra("alunoSelecionado", alunoSelecionado);
				startActivity(irParaFormulario);
				
				return false;
			}
		});
		
		MenuItem deletar = menu.add("Remover");
		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				alunoDAO.remover(alunoSelecionado);
				recarregarLista();
				alunoDAO.close();
				return false;
			}
		});
	}


}
