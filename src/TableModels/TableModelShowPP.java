package TableModels;

import TablesGetSetters.ListProdutosShowPP;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class TableModelShowPP extends AbstractTableModel {

    private List<ListProdutosShowPP> linhas;
    private String[] colunas = new String[]{"ID", "CodeBar", "Descricao", "REF", "SubGrupo", "Marca", "Serial", "Preco", "Promocao", "Quantidade"};
    private Map<String, Integer> quantidadePorNome;

    public TableModelShowPP() {
        linhas = new ArrayList<ListProdutosShowPP>();
        calcularQuantidadePorNome();
    }

    public TableModelShowPP(List<ListProdutosShowPP> lista) {
        linhas = new ArrayList<ListProdutosShowPP>(lista);
        calcularQuantidadePorNome();
    }

    public int getQuantidadePorNome(String nome) {
        if (quantidadePorNome.containsKey(nome)) {
            return quantidadePorNome.get(nome);
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        /*
        switch(columnIndex){
            case 0:
                return Integer.class;
            default:
                return String.class;
        }
         */
        return String.class;
    }

    private void calcularQuantidadePorNome() {
        quantidadePorNome = new HashMap<>();
        for (ListProdutosShowPP produto : linhas) {
            String Descricao = produto.getDescricao();
            int quantidade = produto.getQtde();
            quantidadePorNome.put(Descricao, quantidadePorNome.getOrDefault(Descricao, 0) + quantidade);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ListProdutosShowPP pp = linhas.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return pp.getId();
            case 1:
                return pp.getCodigob();
            case 2:
                return pp.getDescricao();
            case 3:
                return pp.getRef();
            case 4:
                return pp.getSubgrupo();
            case 5:
                return pp.getMarca();
            case 6:
                return pp.getSerial();
            case 7:
                return pp.getPreco();
            case 8:
                return pp.getPromocao();
            case 9:
                return pp.getQtde();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    //modifica na linha e coluna especificada
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ListProdutosShowPP pp = linhas.get(rowIndex);

        switch (columnIndex) {
            case 0:
                pp.setId(aValue.toString());
                break;
            case 1:
                pp.setCodigob(aValue.toString());
                break;
            case 2:
                pp.setDescricao(aValue.toString());
                break;
            case 3:
                pp.setRef(aValue.toString());
                break;
            case 4:
                pp.setSubgrupo(aValue.toString());
                break;
            case 5:
                pp.setMarca(aValue.toString());
                break;
            case 6:
                pp.setSerial(aValue.toString());
                break;
            case 7:
                pp.setPreco(aValue.toString());
                break;
            case 8:
                pp.setPromocao(aValue.toString());
                break;
            case 9:
                pp.setQtde(Integer.parseInt(aValue.toString()));
                break;
            default:
            // Isto não deveria acontecer...             
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    //modifica na linha especificada
    public void setValueAt(ListProdutosShowPP aValue, int rowIndex) {
        ListProdutosShowPP pp = linhas.get(rowIndex); // Carrega o item da linha que deve ser modificado

        pp.setId(aValue.getId());
        pp.setCodigob(aValue.getCodigob());
        pp.setDescricao(aValue.getDescricao());
        pp.setRef(aValue.getRef());
        pp.setSubgrupo(aValue.getSubgrupo());
        pp.setMarca(aValue.getMarca());
        pp.setSerial(aValue.getSerial());
        pp.setPreco(aValue.getPreco());
        pp.setPromocao(aValue.getPromocao());
        pp.setQtde(aValue.getQtde());

        fireTableCellUpdated(rowIndex, 0);
        fireTableCellUpdated(rowIndex, 1);
        fireTableCellUpdated(rowIndex, 2);
        fireTableCellUpdated(rowIndex, 3);
        fireTableCellUpdated(rowIndex, 4);
        fireTableCellUpdated(rowIndex, 5);
        fireTableCellUpdated(rowIndex, 6);
        fireTableCellUpdated(rowIndex, 7);
        fireTableCellUpdated(rowIndex, 8);
        fireTableCellUpdated(rowIndex, 9);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public ListProdutosShowPP getProdutos(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    public void addProdutosPP(ListProdutosShowPP f) {
        // Adiciona o registro.
        linhas.add(f);
        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    /* Remove a linha especificada. */
    public void remove(int indiceLinha) {
        linhas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    /* Adiciona uma lista de Cliente ao final dos registros. */
    public void addLista(List<ListProdutosShowPP> f) {
        // Pega o tamanho antigo da tabela.
        int tamanhoAntigo = getRowCount();

        // Adiciona os registros.
        linhas.addAll(f);
        fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
    }

    public void ordenarPorQuantidade() {
        // Ordena a tabela por quantidade em ordem crescente.
        linhas.sort((a, b) -> Integer.compare(a.getQtde(), b.getQtde()));
        fireTableDataChanged();
    }

// Adicione o seguinte método para atualizar a tabela com uma nova lista de produtos
    public void atualizarListaDeProdutos(List<ListProdutosShowPP> produtos) {
        linhas.clear(); // Limpa os dados atuais da tabela
        linhas.addAll(produtos); // Adiciona os novos produtos à tabela
        calcularQuantidadePorNome(); // Recalcula a quantidade por nome
        fireTableDataChanged(); // Notifica a tabela de que os dados foram alterados
    }

    /* Remove todos os registros. */
    public void limpar() {
        linhas.clear();
        fireTableDataChanged();
    }

    /* Verifica se este table model esta vazio. */
    public boolean isEmpty() {
        return linhas.isEmpty();
    }

}
