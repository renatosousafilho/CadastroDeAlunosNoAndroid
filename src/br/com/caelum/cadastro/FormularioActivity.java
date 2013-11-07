package br.com.caelum.cadastro;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.modelo.Aluno;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;

public class FormularioActivity extends Activity {
	private FormularioHelper helper;
	private Aluno alunoParaSerAlterado;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.formulario);
		Button botao = (Button) findViewById(R.id.botao);
		
		alunoParaSerAlterado = (Aluno) getIntent().getSerializableExtra("alunoSelecionado");
		
		helper = new FormularioHelper(this);
		if (alunoParaSerAlterado!=null){
			botao.setText("alterar");
			helper.colocarAlunoNoFormulario(alunoParaSerAlterado);
		}
		
		
		botao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AlunoDAO alunoDAO = new AlunoDAO(FormularioActivity.this);
				Aluno aluno = helper.pegaAlunoDoFormulario();
				if (alunoParaSerAlterado==null){
					Toast.makeText(FormularioActivity.this, "Aluno cadastrado com sucesso", Toast.LENGTH_LONG).show();
					alunoDAO.insere(aluno);
				} else {
					aluno.setId(alunoParaSerAlterado.getId());
					alunoDAO.editar(aluno);
				}
				
				alunoDAO.close();
				finish();
			}
		});
		
	}


}
