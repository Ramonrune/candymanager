package com.candymanager.pedidos;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.candymanager.R;
import com.candymanager.produtos.ProdutoModel;
import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PedidoListaView {

    //private RecyclerView pedidoRecyclerView;
    //private FastScroller scrollerRapidoFastScroller;
    private FloatingActionButton novoPedidoFloatingActionButton;
    private AgendaCalendarView calendarioAgendaCalendarView;
    //private PedidoAdapter pedidoAdapter;


    public PedidoListaView(View view, ArrayList<PedidoModel> lista) {

        //pedidoRecyclerView = (RecyclerView) view.findViewById(R.id.listaProdutoRecyclerView);
        //scrollerRapidoFastScroller = (FastScroller) view.findViewById(R.id.produtoFastScroller);

        //pedidoRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //pedidoAdapter =  new PedidoAdapter(view.getContext(), lista);
        //pedidoRecyclerView.setAdapter(pedidoAdapter);
        //scrollerRapidoFastScroller.setRecyclerView(pedidoRecyclerView);

        novoPedidoFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.novoPedidoFloatingActionButton);
        calendarioAgendaCalendarView = (AgendaCalendarView) view.findViewById(R.id.agenda_calendar_view);

    }


   /* public RecyclerView getPedidoRecyclerView() {
        return pedidoRecyclerView;
    }
    */

    public FloatingActionButton getNovoPedidooFloatingActionButton() {
        return novoPedidoFloatingActionButton;
    }

    public AgendaCalendarView getCalendarioAgendaCalendarView() {
        return calendarioAgendaCalendarView;
    }
/*
    public PedidoAdapter getPedidoAdapter() {
        return pedidoAdapter;
    }

    */


}
