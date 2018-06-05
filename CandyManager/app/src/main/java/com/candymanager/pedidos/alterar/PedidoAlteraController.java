package com.candymanager.pedidos.alterar;


import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.candymanager.R;
import com.candymanager.cliente.ClienteDAO;
import com.candymanager.cliente.ClienteModel;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.pedidos.ItemPedidoModel;
import com.candymanager.pedidos.PedidoController;
import com.candymanager.pedidos.PedidoDAO;
import com.candymanager.pedidos.PedidoModel;
import com.candymanager.pedidos.adapter.ClienteAdapter;
import com.candymanager.pedidos.adapter.ProdutoAdapter;
import com.candymanager.produtos.ProdutoDAO;
import com.candymanager.produtos.ProdutoModel;
import com.candymanager.util.BitmapUtil;
import com.candymanager.util.Mensagem;
import com.candymanager.util.UUIDGenerator;
import com.candymanager.util.Validation;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

public class PedidoAlteraController extends Fragment {

    private View view;
    private PedidoDAO pedidoDAO;
    private ProdutoDAO produtoDAO;
    private ClienteDAO clienteDAO;
    private AlertDialog dialogClienteAlertDialog;
    private AlertDialog dialogProdutoAlertDialog;
    private int quantidadeItemsPedido = 0;
    private Validation validacao = new Validation();
    private PedidoModel pedidoModel;

    private PedidoAlteraView pedidoAlteraView;

    public PedidoAlteraController() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Pedido");

        inicializaRecursos(inflater, container);

        carregaItemDePedido();
        inicializaListeners();

        return view;
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_pedido_altera, container, false);
        pedidoAlteraView = new PedidoAlteraView(view);

        pedidoDAO = new PedidoDAO(view.getContext());
        produtoDAO = new ProdutoDAO(view.getContext());
        clienteDAO = new ClienteDAO(view.getContext());

    }

    public void setPedidoModel(PedidoModel pedidoModel) {
        this.pedidoModel = pedidoModel;
    }

    private void carregaItemDePedido(){

        pedidoAlteraView.getClienteImageView().setImageBitmap(BitmapUtil.getImage(pedidoModel.getCliente().getImagem()));
        Date date = new Date(pedidoModel.getData());
        pedidoAlteraView.getDataDatePicker().updateDate((date.getYear() + 1900), date.getMonth(), date.getDate());

        pedidoAlteraView.getClienteButton().setText(pedidoModel.getCliente().getNome());
        try {
            StringBuilder builder = new StringBuilder(pedidoModel.getCep());
            builder.insert(5, "-");

            pedidoAlteraView.getCepEditText().setText(builder.toString());
        }catch(Exception e){

        }
        pedidoAlteraView.getEnderecoEditText().setText(pedidoModel.getEndereco());
        pedidoAlteraView.getBairroEditText().setText(pedidoModel.getBairro());
        pedidoAlteraView.getNumeroEditText().setText(pedidoModel.getNumero());

        for(int i = 0; i < pedidoModel.getListaItemsDePedido().size(); i++){

            ItemPedidoModel itemPedidoModel = pedidoModel.getListaItemsDePedido().get(i);

            View newLayout = getLayoutInflater().inflate(R.layout.layout_item_pedido, null, false);
            itemPedido = new ItemPedidoAlteraView(newLayout);

            LinearLayout ll = (LinearLayout) newLayout.findViewById(R.id.pedidoLinearLayout);
            TextView numeroPedidoTextView = (TextView) newLayout.findViewById(R.id.numeroItemPedidoTextView);
            numeroPedidoTextView.setText((i + 1) + "º item de pedido");
            itemPedido.setNumeroItemPedido(numeroPedidoTextView);

            Button doce = (Button) newLayout.findViewById(R.id.doceButton);
            doce.setText(itemPedidoModel.getDoce().getNome());
            itemPedido.setDoceButton(doce);


            //Levi

/*
            ItemPedidoAlteraView pedidoItemPedidoAlteraView = pedidoAlteraView.getListaPedidos().get(i);

            System.out.println("Qtd pedido1: " + pedidoItemPedidoAlteraView.getProdutoModel());

            if (pedidoItemPedidoAlteraView.getProdutoModel() == null);
*/
            DiscreteSeekBar discreteSeekBar = (DiscreteSeekBar) newLayout.findViewById(R.id.quantidadeDoceDiscreteSeekBar);
            discreteSeekBar.setProgress(itemPedidoModel.getQuantidade());
            itemPedido.setQuantidadeDoceDiscreteSeekBar(discreteSeekBar);

            EditText valorTotal = (EditText) newLayout.findViewById(R.id.valorTotalDosGastosEditText);
            valorTotal.setText(String.format("%.2f", itemPedidoModel.getValorTotalGasto()));
            itemPedido.setValorTotalDosGastosEditText(valorTotal);

            DiscreteSeekBar margemDeLucro = (DiscreteSeekBar) newLayout.findViewById(R.id.margemDeLucroDiscreteSeekBar);
            margemDeLucro.setProgress(itemPedidoModel.getMargemLucro());
            itemPedido.setMargemDeLucroDiscreteSeekBar(margemDeLucro);

            EditText precoDeVenda = (EditText) newLayout.findViewById(R.id.precoDeVendaEditText);

            double valorDeVenda = itemPedidoModel.getValorTotalGasto() * ((float)itemPedidoModel.getMargemLucro() / 100.00);

            precoDeVenda.setText(String.format("%.2f", (itemPedidoModel.getValorTotalGasto() + valorDeVenda)));
            itemPedido.setPrecoDeVendaEditText(precoDeVenda);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            layoutParams.setMargins(15, 15, 15, 15);
            ll.setLayoutParams(layoutParams);

            pedidoAlteraView.getMainLinearLayout().addView(newLayout, 2);

            quantidadeItemsPedido++;

            registraItemPedidoNovo(newLayout);

            pedidoAlteraView.getListaPedidos().get(i).setProdutoModel(produtoDAO.getProduto(itemPedidoModel.getDoce().getIdProduto()));
        }

        pedidoModel.getListaItemsDePedido().removeAll(pedidoModel.getListaItemsDePedido());

    }


    private void inicializaListeners() {

        pedidoAlteraView.getClienteButton().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final ArrayList<ClienteModel> lista = clienteDAO.getLista();

                if(lista.size() != 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View row = inflater.inflate(R.layout.lista_clientes, null);
                    ListView l1 = (ListView) row.findViewById(R.id.listaClientesListView);
                    l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                            pedidoModel.setCliente(lista.get(pos));
                            pedidoAlteraView.getClienteButton().setError(null);
                            pedidoAlteraView.getClienteButton().setText(lista.get(pos).getNome());
                            pedidoAlteraView.getClienteImageView().setImageBitmap(BitmapUtil.getImage(lista.get(pos).getImagem()));
                            dialogClienteAlertDialog.dismiss();
                        }
                    });
                    l1.setAdapter(new ClienteAdapter(getContext(), lista));
                    builder.setView(row);

                    dialogClienteAlertDialog = builder.create();
                    dialogClienteAlertDialog.show();

                }
                else{
                    Mensagem.mostrarDialogoFragment(getActivity(), "Clientes", "Insira ao menos um cliente antes de prosseguir!");

                }
            }
        });

        pedidoAlteraView.getAgendaImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantidadeItemsPedido++;

                View newLayout = getLayoutInflater().inflate(R.layout.layout_item_pedido, null, false);

                LinearLayout ll = (LinearLayout) newLayout.findViewById(R.id.pedidoLinearLayout);
                TextView numeroPedidoTextView = (TextView) newLayout.findViewById(R.id.numeroItemPedidoTextView);
                numeroPedidoTextView.setText(quantidadeItemsPedido + "º item de pedido");

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                layoutParams.setMargins(15, 15, 15, 15);
                ll.setLayoutParams(layoutParams);

                pedidoAlteraView.getMainLinearLayout().addView(newLayout, 2);

                registraItemPedidoNovo(newLayout);

            }
        });

        pedidoAlteraView.getCepEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("-----");
                if (charSequence.toString().trim().length() == 9 && charSequence.charAt(5) == '-') {

                    CepAsyncTask httpCepUtil = new CepAsyncTask();
                    httpCepUtil.execute(charSequence.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.check).setVisible(true);

    }

    private ItemPedidoAlteraView itemPedido;

    private void registraItemPedidoNovo(final View newLayout) {

        itemPedido = new ItemPedidoAlteraView(newLayout);

        pedidoAlteraView.getListaPedidos().add(itemPedido);

        final ItemPedidoAlteraView item = itemPedido;

        itemPedido.getDoceButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ArrayList<ProdutoModel> lista = produtoDAO.getLista();

                if(lista.size() != 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View row = inflater.inflate(R.layout.lista_produtos, null);
                    ListView l1 = (ListView) row.findViewById(R.id.listaProdutosListView);

                    l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                            item.getDoceButton().setText(lista.get(pos).getNome());
                            item.setProdutoModel(lista.get(pos));
                            item.getDoceButton().setError(null);
                            dialogProdutoAlertDialog.dismiss();
                        }
                    });

                    l1.setAdapter(new ProdutoAdapter(getContext(), lista));
                    builder.setView(row);

                    dialogProdutoAlertDialog = builder.create();
                    dialogProdutoAlertDialog.show();
                }
                else{
                    Mensagem.mostrarDialogoFragment(getActivity(), "Produtos", "Insira ao menos um produto antes de prosseguir!");
                }
            }
        });


        itemPedido.getExcluirItemPedidoImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantidadeItemsPedido--;

                pedidoAlteraView.getListaPedidos().remove(item);
                pedidoAlteraView.getMainLinearLayout().removeView(newLayout);

                for (int i = 0; i < pedidoAlteraView.getListaPedidos().size(); i++) {
                    pedidoAlteraView.getListaPedidos().get(i).getNumeroItemPedido().setText((i + 1) + "º item de pedido");
                }
            }
        });

        itemPedido.getValorTotalDosGastosEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adicionaListenerValor(item, item.getMargemDeLucroDiscreteSeekBar().getProgress());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) { }

        });

        itemPedido.getMargemDeLucroDiscreteSeekBar().setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                item.getValorTotalDosGastosEditText().setError(null);
                adicionaListenerValor(item, value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) { }

        });
    }

    private void adicionaListenerValor(ItemPedidoAlteraView itemPedido, int value) {
        if (!itemPedido.getValorTotalDosGastosEditText().getText().toString().trim().equals("")) {
            try {

                NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
                Number number = format.parse(itemPedido.getValorTotalDosGastosEditText().getText().toString());

                double valor = number.doubleValue();
                double novoValor = valor * ((float) (value / 100.00));

                itemPedido.getPrecoDeVendaEditText().setText("R$ " + String.format("%.2f", valor + novoValor));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            itemPedido.getPrecoDeVendaEditText().setText("");
        }
    }


    //Inserção (Aquela setinha lá em cima do menu)

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.check:

                boolean sucesso = true;

                if (pedidoModel.getCliente() == null) {
                    pedidoAlteraView.getClienteButton().setError("Informe o cliente corretamente!");
                    sucesso = false;
                }

                if (sucesso) {
                    if (pedidoAlteraView.getListaPedidos().size() == 0) {
                        Mensagem.mostrarDialogoFragment(getActivity(), "Item de pedido", "Adicione ao menos um item de pedido!");

                        sucesso = false;
                    }
                }

                if (sucesso) {
                    for (int i = 0; i < pedidoAlteraView.getListaPedidos().size(); i++) {

                        ItemPedidoAlteraView pedidoItemPedidoAlteraView = pedidoAlteraView.getListaPedidos().get(i);

                        if (pedidoItemPedidoAlteraView.getProdutoModel() == null) {

                            pedidoItemPedidoAlteraView.getDoceButton().setError("Informe o doce que deseja no item de pedido!");
                            sucesso = false;
                            break;
                        }

                        if (pedidoItemPedidoAlteraView.getValorTotalDosGastosEditText().getText().toString().trim().equals("")) {
                            pedidoItemPedidoAlteraView.getValorTotalDosGastosEditText().setError("Informe o valor total dos gastos!");
                            sucesso = false;
                            break;
                        }

                        if(!pedidoItemPedidoAlteraView.getValorTotalDosGastosEditText().getText().toString().trim().equals("")){

                            try{
                                Double.parseDouble(pedidoItemPedidoAlteraView.getValorTotalDosGastosEditText().getText().toString().replace(",","."));
                            }
                            catch(Exception e){
                                pedidoItemPedidoAlteraView.getValorTotalDosGastosEditText().setError("Problema de conversão no valor informado, insira outro valor!");
                                sucesso = false;
                                break;
                            }
                        }
                    }
                }

                if (sucesso) {
                    LinkedHashMap<EditText, String> lista = new LinkedHashMap<>();

                    lista.put(pedidoAlteraView.getEnderecoEditText(), "Informe o endereço de entrega!");
                    lista.put(pedidoAlteraView.getBairroEditText(), "Informe o bairro de entrega!");
                    lista.put(pedidoAlteraView.getNumeroEditText(), "Informe o número de entrega!");

                    sucesso = validacao.validaNaoNulo(lista);
                }

                if (sucesso) {
                    pedidoModel.setBairro(pedidoAlteraView.getBairroEditText().getText().toString());
                    pedidoModel.setEndereco(pedidoAlteraView.getEnderecoEditText().getText().toString());
                    pedidoModel.setNumero(pedidoAlteraView.getNumeroEditText().getText().toString());
                    pedidoModel.setCep(pedidoAlteraView.getCepEditText().getText().toString().replace("-", ""));

                    Calendar cal = Calendar.getInstance();
                    cal.set(pedidoAlteraView.getDataDatePicker().getYear(), pedidoAlteraView.getDataDatePicker().getMonth(), pedidoAlteraView.getDataDatePicker().getDayOfMonth());

                    pedidoModel.setData(cal.getTimeInMillis());
                }

                boolean insercao = false;

                if (sucesso) {
                    for (int i = 0; i < pedidoAlteraView.getListaPedidos().size(); i++) {
                        ItemPedidoAlteraView pedidoItemPedidoAlteraView = pedidoAlteraView.getListaPedidos().get(i);

                        ItemPedidoModel itemPedidoModel = new ItemPedidoModel();

                        itemPedidoModel.setDoce(pedidoItemPedidoAlteraView.getProdutoModel());
                        itemPedidoModel.setIdItemPedido(UUIDGenerator.uuid());
                        itemPedidoModel.setMargemLucro(pedidoItemPedidoAlteraView.getMargemDeLucroDiscreteSeekBar().getProgress());
                        itemPedidoModel.setQuantidade(pedidoItemPedidoAlteraView.getQuantidadeDoceDiscreteSeekBar().getProgress());
                        itemPedidoModel.setValorTotalGasto(Double.parseDouble(pedidoItemPedidoAlteraView.getValorTotalDosGastosEditText().getText().toString().replace(",",".")));

                        pedidoModel.getListaItemsDePedido().add(itemPedidoModel);
                    }

                    pedidoDAO.excluir(pedidoModel);

                    insercao = pedidoDAO.cadastrar(pedidoModel);
                }

                PedidoController pedidoController = new PedidoController();

                if (sucesso) {

                    if (insercao) {
                        Mensagem.mostrarDialogoMudarFragmento(pedidoController, getActivity(), "Sucesso", "Cadastro do pedido e dos itens do pedido realizado com sucesso!");
                    } else {
                        Mensagem.mostrarDialogoMudarFragmento(pedidoController, getActivity(), "Erro", "Ocorreu algum erro na inserção, por favor, tente novamente ou entre em contato com o suporte!");
                    }
                }
                return true;

            default:
                break;
        }

        return false;
    }

    class CepAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... cep) {
            return requestCep(cep[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            if (json != null) {
                try {
                    formataJson(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private String requestCep(String cep) {
            Uri builtUri = Uri.parse("https://viacep.com.br/ws/" + cep.toString().replaceAll("[^\\d.]", "") + "/json/").buildUpon()
                    .build();

            try {
                URL url = new URL(builtUri.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                String jsonStr = buffer.toString();


                return jsonStr;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        private void formataJson(String jsonStr) throws JSONException {
            JSONObject jsonObject = new JSONObject(jsonStr);

            String logradouro = jsonObject.getString("logradouro");
            String bairro = jsonObject.getString("bairro");
            String localidade = jsonObject.getString("localidade");
            String uf = jsonObject.getString("uf");

            pedidoAlteraView.getEnderecoEditText().setText(logradouro + " - " + localidade + " - " + uf);
            pedidoAlteraView.getBairroEditText().setText(bairro);

        }


    }


}
