package com.candymanager.pedidos.cadastrar;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.candymanager.R;
import com.candymanager.cliente.ClienteController;
import com.candymanager.cliente.ClienteDAO;
import com.candymanager.cliente.ClienteModel;
import com.candymanager.pedidos.ItemPedidoModel;
import com.candymanager.pedidos.PedidoController;
import com.candymanager.pedidos.PedidoDAO;
import com.candymanager.pedidos.PedidoModel;
import com.candymanager.pedidos.adapter.ClienteAdapter;
import com.candymanager.pedidos.adapter.ProdutoAdapter;
import com.candymanager.produtos.ProdutoDAO;
import com.candymanager.produtos.ProdutoModel;
import com.candymanager.util.BitmapUtil;
import com.candymanager.util.CepAsyncTask;
import com.candymanager.util.Mensagem;
import com.candymanager.util.UUIDGenerator;
import com.candymanager.util.Validation;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;


public class PedidoAdicionaController extends Fragment {

    private View view;
    private PedidoDAO pedidoDAO;
    private ProdutoDAO produtoDAO;
    private ClienteDAO clienteDAO;
    private AlertDialog dialogClienteAlertDialog;
    private AlertDialog dialogProdutoAlertDialog;
    private int quantidadeItemsPedido = 0;
    private Validation validacao = new Validation();
    private PedidoModel pedidoModel = new PedidoModel();


    private PedidoAdicionaView pedidoAdicionaView;

    public PedidoAdicionaController() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        inicializaRecursos(inflater, container);
        inicializaListeners();

        return view;
    }

    private void inicializaListeners() {
        pedidoAdicionaView.getClienteButton().setOnClickListener(new View.OnClickListener() {
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
                            pedidoAdicionaView.getClienteButton().setError(null);
                            pedidoAdicionaView.getClienteButton().setText(lista.get(pos).getNome());
                            pedidoAdicionaView.getClienteImageView().setImageBitmap(BitmapUtil.getImage(lista.get(pos).getImagem()));
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


        pedidoAdicionaView.getAgendaImageButton().setOnClickListener(new View.OnClickListener() {
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

                pedidoAdicionaView.getMainLinearLayout().addView(newLayout, 2);
                registraItemPedidoNovo(newLayout);

                /*for(int i = 0; i < pedidoAdicionaView.getListaPedidos().size(); i++){
                    if(pedidoAdicionaView.getListaPedidos().get(i).getNumeroItemPedido() != null){
                        pedidoAdicionaView.getListaPedidos().get(i).getNumeroItemPedido().setText((i) + "º item de pedido");
                    }
                }

*/

            }
        });

        pedidoAdicionaView.getAgendaImageButton().performClick();
        pedidoAdicionaView.getCepMaskedEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("-----");
                if (charSequence.toString().trim().length() == 9) {

                    CepAsyncTask httpCepUtil = new CepAsyncTask();
                    httpCepUtil.execute(charSequence.toString());


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_pedido_adiciona, container, false);
        pedidoAdicionaView = new PedidoAdicionaView(view);
        pedidoDAO = new PedidoDAO(view.getContext());
        produtoDAO = new ProdutoDAO(view.getContext());
        clienteDAO = new ClienteDAO(view.getContext());


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.check).setVisible(true);

    }

    private PedidoItemPedidoAdicionaView itemPedido;

    private void registraItemPedidoNovo(final View newLayout) {
        itemPedido = new PedidoItemPedidoAdicionaView(newLayout);
        pedidoAdicionaView.getListaPedidos().add(itemPedido);
        // pedidoModel.getListaItemsDePedido().add(itemPedido);
        final PedidoItemPedidoAdicionaView item = itemPedido;
        System.out.println(pedidoAdicionaView.getListaPedidos().size());


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
                pedidoAdicionaView.getListaPedidos().remove(item);
                pedidoAdicionaView.getMainLinearLayout().removeView(newLayout);
                for (int i = 0; i < pedidoAdicionaView.getListaPedidos().size(); i++) {
                    pedidoAdicionaView.getListaPedidos().get(i).getNumeroItemPedido().setText((i + 1) + "º item de pedido");
                    //pedidoAdicionaView.getListaPedidos().get(i).getNumeroItemPedido().setText(i + 1);
                }

            }
        });

        itemPedido.getValorTotalDosGastosEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                adicionaListenerValor(item, item.getMargemDeLucroDiscreteSeekBar().getProgress());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        itemPedido.getMargemDeLucroDiscreteSeekBar().setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                item.getValorTotalDosGastosEditText().setError(null);
                adicionaListenerValor(item, value);


            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
    }

    private void adicionaListenerValor(PedidoItemPedidoAdicionaView itemPedido, int value) {
        if (!itemPedido.getValorTotalDosGastosEditText().getText().toString().trim().equals("")) {
            try {
                double valor = Double.parseDouble(
                        itemPedido.getValorTotalDosGastosEditText().getText().toString().replaceAll("[^0-9\\.]", ""));
                double novoValor = valor * ((float) (value / 100.00));

                itemPedido.getPrecoDeVendaEditText().setText("R$ " + String.format("%.2f", valor + novoValor));
            } catch (Exception e) {

            }
        } else {
            itemPedido.getPrecoDeVendaEditText().setText("");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.check:

                boolean sucesso = true;

                if (pedidoModel.getCliente() == null) {
                    pedidoAdicionaView.getClienteButton().setError("Informe o cliente corretamente!");
                    sucesso = false;
                }

                if (sucesso) {
                    if (pedidoAdicionaView.getListaPedidos().size() == 0) {
                        Mensagem.mostrarDialogoFragment(getActivity(), "Item de pedido", "Adicione ao menos um item de pedido!");

                        sucesso = false;
                    }
                }

                if (sucesso) {
                    for (int i = 0; i < pedidoAdicionaView.getListaPedidos().size(); i++) {

                         PedidoItemPedidoAdicionaView pedidoItemPedidoAdicionaView = pedidoAdicionaView.getListaPedidos().get(i);

                        if (pedidoItemPedidoAdicionaView.getProdutoModel() == null) {

                            pedidoItemPedidoAdicionaView.getDoceButton().setError("Informe o doce que deseja no item de pedido!");
                            sucesso = false;
                            break;
                        }

                        if (pedidoItemPedidoAdicionaView.getValorTotalDosGastosEditText().getText().toString().trim().equals("")) {
                            pedidoItemPedidoAdicionaView.getValorTotalDosGastosEditText().setError("Informe o valor total dos gastos!");
                            sucesso = false;
                            break;
                        }

                        if(!pedidoItemPedidoAdicionaView.getValorTotalDosGastosEditText().getText().toString().trim().equals("")){

                            try{
                                Integer.parseInt(pedidoItemPedidoAdicionaView.getValorTotalDosGastosEditText().getText().toString());
                            }
                            catch(Exception e){
                                pedidoItemPedidoAdicionaView.getValorTotalDosGastosEditText().setError("Problema de conversão no valor informado, insira outro valor!");
                                sucesso = false;
                                break;
                            }


                        }

                    }
                }


                if (sucesso) {

                    LinkedHashMap<EditText, String> lista = new LinkedHashMap<>();

                    lista.put(pedidoAdicionaView.getEnderecoEditText(), "Informe o endereço de entrega!");
                    lista.put(pedidoAdicionaView.getBairroEditText(), "Informe o bairro de entrega!");
                    lista.put(pedidoAdicionaView.getNumeroEditText(), "Informe o número de entrega!");

                    sucesso = validacao.validaNaoNulo(lista);

                }

                if (sucesso) {

                    pedidoModel.setCep(pedidoAdicionaView.getCepMaskedEditText().getUnmaskedText());
                    pedidoModel.setBairro(pedidoAdicionaView.getBairroEditText().getText().toString());
                    pedidoModel.setEndereco(pedidoAdicionaView.getEnderecoEditText().getText().toString());
                    pedidoModel.setNumero(pedidoAdicionaView.getNumeroEditText().getText().toString());

                    Calendar cal = Calendar.getInstance();
                    cal.set(pedidoAdicionaView.getDataDatePicker().getYear(), pedidoAdicionaView.getDataDatePicker().getMonth(), pedidoAdicionaView.getDataDatePicker().getDayOfMonth());

                    pedidoModel.setData(cal.getTimeInMillis());
                }
                boolean insercao = false;

                if (sucesso) {

                    for (int i = 0; i < pedidoAdicionaView.getListaPedidos().size(); i++) {
                        PedidoItemPedidoAdicionaView pedidoItemPedidoAdicionaView = pedidoAdicionaView.getListaPedidos().get(i);

                        ItemPedidoModel itemPedidoModel = new ItemPedidoModel();
                        itemPedidoModel.setDoce(pedidoItemPedidoAdicionaView.getProdutoModel());
                        itemPedidoModel.setIdItemPedido(UUIDGenerator.uuid());
                        itemPedidoModel.setMargemLucro(pedidoItemPedidoAdicionaView.getMargemDeLucroDiscreteSeekBar().getProgress());
                        itemPedidoModel.setQuantidade(pedidoItemPedidoAdicionaView.getQuantidadeDoceDiscreteSeekBar().getProgress());
                        itemPedidoModel.setValorTotalGasto(Double.parseDouble(pedidoItemPedidoAdicionaView.getValorTotalDosGastosEditText().getText().toString()));

                        pedidoModel.getListaItemsDePedido().add(itemPedidoModel);
                    }

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

            pedidoAdicionaView.getEnderecoEditText().setText(logradouro + " - " + localidade + " - " + uf);
            pedidoAdicionaView.getBairroEditText().setText(bairro);

        }


    }


}
