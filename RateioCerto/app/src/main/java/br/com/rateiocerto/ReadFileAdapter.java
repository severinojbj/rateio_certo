package br.com.rateiocerto;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

/**
 * Rateio Certo
 *
 * ReadFileAdapter: classe que gerencia a visualização dos elementos da lista RecicleView.
 * É onde se realiza o controle sobre quais linhas, cada uma representam uma planilha,
 * aparecerão na tela durante a rolagem do usuário.
 *
 * @Author: Severino José (biujose@gmail.com)
 */

public class ReadFileAdapter extends
        RecyclerView.Adapter <ReadFileAdapter.ReadFileHolder> {

    public String [] mDataset;
    public Context c;

    /**
     * ReadFileHolder: classe interna ao adaptador, responsável pela inserção de cada linha
     * da lista RecyclerView. No caso desta aplicação, cada linha corresponde a um container
     * contendo um ícone e um TextView com o nome da planilha.
     */
    public static class ReadFileHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        /**
         * Construtor da classe interna. É onde ocorre a criação de uma linha da lista
         * RecycleView. Como precisamos abrir o arquivo representado numa linha, adicionamos
         * na mesma uma rotina de ativação para abertura de arquivo, no formato FileProvider.
         * @param v
         */
        public ReadFileHolder (View v) {
            super (v);
            v.setOnClickListener(new View.OnClickListener() {
                /**
                 * Ação de toque para abertura do arquivo representado pelo TextView. O nome
                 * contido no elemento visual de texto, junto ao seu caminh de pasta, será
                 * dado como parâmetro paa um provedor de abertura de arquivos, o FileProvider.
                 * Nessa classe, ocorre a anáise do tipo de arquivo, bem como os apps instalados
                 * no smartphone capazes de abri-lo.
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    Context c = v.getContext();
                    TextView t = v.findViewById(R.id.txt_read_view);
                    String filePath = c.getExternalFilesDir("output")
                            + "/" + t.getText();
                    File file = new File (filePath);
                    final Uri data = FileProvider.getUriForFile(c, "br.com.rateiocerto.myprovider", file);
                    c.grantUriPermission(c.getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    final Intent intent = new Intent(Intent.ACTION_VIEW)
                            .setDataAndType(data, "text/tab-separated-values")
                            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    c.startActivity(intent);

                }
            });
            this.textView = v.findViewById(R.id.txt_read_view);
        }
    }

    /**
     * Construtor da classe. Nele é carregado a lista de arquivos contidos na pasta
     * de planilhas, cuja representaão se dá como um array de String passado como
     * parâmetro.
     * @param mDataset array de String contendo a lista de planilhas na pasta
     */
    public ReadFileAdapter (String  [] mDataset) {
        this.mDataset = mDataset;
    }

    /**
     * Método de criação de cada linha da lista RecyclerView. É feita uma associação
     * do layout de linha com um novo objeto Holder. Esse objeto é então retornado
     * para o gerenciador da lista.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ReadFileAdapter.ReadFileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
               R.layout.activity_file_row, parent, false);
        ReadFileHolder rdHolder = new ReadFileHolder(v);
        return rdHolder;
    }

    /**
     * Método de nomeação de cada linha de uma lista RecyclerView. Essa nomeação se dá pela posição
     * da lista de referência, carregada no construtor. As posições são atualizadas
     * de acordo com a rolagem feita pelo usuário na tela.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ReadFileHolder holder, int position) {
        holder.textView.setText(mDataset[position]);
    }

    /**
     * Método que retorna o tamanho da lista de referência inserida no construtor.
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
