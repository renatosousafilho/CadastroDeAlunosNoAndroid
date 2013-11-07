package br.com.caelum.cadastro;

import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import br.com.caelum.modelo.Aluno;

public class FormularioHelper {
	private Aluno aluno;
	private EditText editNome;
	private EditText editTelefone;
	private EditText editEndereco;
	private EditText editSite;
	private SeekBar seekNota;
	
	public FormularioHelper(FormularioActivity activity) {
		editNome = (EditText) activity.findViewById(R.id.nome);
		editTelefone = (EditText) activity.findViewById(R.id.telefone);
		editEndereco =  (EditText) activity.findViewById(R.id.endereco);
		editSite = (EditText) activity.findViewById(R.id.site);
		seekNota = (SeekBar) activity.findViewById(R.id.nota);
		
		aluno = new Aluno();
	}
	
	public Aluno pegaAlunoDoFormulario(){
		
		aluno.setNome(editNome.getText().toString());
		aluno.setTelefone(editTelefone.getText().toString());
		aluno.setEndereco(editEndereco.getText().toString());
		aluno.setSite(editSite.getText().toString());
		aluno.setNota(Double.valueOf(seekNota.getProgress()));
		return aluno;
	}
	
	public void colocarAlunoNoFormulario(Aluno aluno){
		editNome.setText(aluno.getNome());
		editTelefone.setText(aluno.getTelefone());
		editEndereco.setText(aluno.getEndereco());
		editSite.setText(aluno.getSite());
		seekNota.setProgress((int) aluno.getNota());
	}
}
