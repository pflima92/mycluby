package br.com.techfullit.mycluby.common.models;

import java.text.DecimalFormat;

public class Report {

    private String category;
    private String product;
    private int productId;
    private String value;
    private int qtVenda;
    private double valor;

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }

    public String getProduct() {
	return product;
    }

    public void setProduct(String product) {
	this.product = product;
    }

    public String getValue() {
	return formatValue(getValor());
    }

    public void setValue(String value) {
	this.value = value;
    }

    public int getQtVenda() {
	return qtVenda;
    }

    public void setQtVenda(int qtVenda) {
	this.qtVenda = qtVenda;
    }

    public double getValor() {
	return valor;
    }

    public void setValor(double valor) {
	this.valor = valor;
    }

    public void addValor(double valor) {
	this.qtVenda++	;
	this.valor += valor;
    }

    public String formatValue(double value) {
	return "R$" + new DecimalFormat("#,###,##0.00").format(value);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    

}
