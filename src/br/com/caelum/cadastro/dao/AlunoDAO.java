package br.com.caelum.cadastro.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.caelum.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {
	private static final String DATABASE = "CadastroCaelum";
	private static final int VERSION = 1;
	private static final String TABELA = "Alunos";
	
	public AlunoDAO(Context context) {
		super(context, DATABASE, null, VERSION);
	}
	
	private ContentValues toValues(Aluno aluno) {
		ContentValues values = new ContentValues();
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("foto", aluno.getFoto());
		return values;
	}

	public void insere(Aluno aluno) {
		getWritableDatabase().insert(TABELA, null, toValues(aluno));
	}
	
	public List<Aluno> lista() {
		String[] columns = {"id","nome","telefone","endereco","site","nota","foto"};
		Cursor cursor = getReadableDatabase().query(TABELA, columns, null, null, null, null, null);
		List<Aluno> alunos = new ArrayList<Aluno>();
		while (cursor.moveToNext()) {
			Aluno aluno = new Aluno();
	
			aluno.setId(Long.valueOf(cursor.getString(0)));
			aluno.setNome(cursor.getString(1));
			aluno.setTelefone(cursor.getString(2));
			aluno.setEndereco(cursor.getString(3));
			aluno.setSite(cursor.getString(4));
			aluno.setNota(cursor.getDouble(5));
			aluno.setFoto(cursor.getString(6));
			
			alunos.add(aluno);
			
		}
		cursor.close();
		return alunos;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABELA + "(id INTEGER PRIMARY KEY, "
				+ "nome TEXT, telefone TEXT, endereco TEXT, "
				+ "site TEXT, nota REAL, foto TEXT);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Alunos");
		onCreate(db);
	}

	public void remover(Aluno aluno) {
		String[] args = {aluno.getId().toString()};
		getWritableDatabase().delete(TABELA, "id=?", args );
		
	}

	public void editar(Aluno aluno) {
		String[] args = {aluno.getId().toString()};
		getWritableDatabase().update(TABELA, toValues(aluno), "id=?", args );
	}


}
