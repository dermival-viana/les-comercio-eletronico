package com.adminportal.core.business.book;

import com.adminportal.core.IStrategy;
import com.adminportal.domain.Book;
import com.adminportal.domain.DomainEntity;

import java.sql.SQLException;

/* Regras de Negocio para Validacao dos dados dos Livros */

public class ValidateDataBook implements IStrategy {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public String process(DomainEntity entity) throws SQLException {
        Book b = (Book) entity;
        StringBuilder msg = new StringBuilder();


        if (b.getTitle().equals("") || b.getTitle().equals(null)) {
            msg.append("Titulo do livro e requerido!");
        }
        if (b.getAuthor().equals("") || b.getAuthor() == null) {
            msg.append("Autor do Livro e requirido!");
        }
        if (b.getPublisher().equals("") || b.getPublisher().equals(null)) {
            msg.append("Editora do Livro e requerida!");
        }
        if (b.getPublicationDate().equals("") || b.getPublicationDate().equals(null)) {
            msg.append("Data da Publicacao e requerida!");
        }
        if (b.getLanguage().equals("") || b.getLanguage().equals(null)) {
            msg.append("A lingua do Livro e requerida!");
        }
        if (b.getCategory().equals("") || b.getCategory().equals(null)) {
            msg.append("A Categoria do Livro e requerida!");
        }
        if (b.getNumberOfPages() == 0) {
            msg.append("O numero de paginas do Livro e requerido!");
        }
        if (b.getFormat().equals("") || b.getFormat() == null) {
            msg.append("O formato do Livro e requerido!");
        }

        if (b.getIsbn() == 0.0) {
            msg.append("O ISBN do Livro e requerido!");
        }

        if (b.getHeight() == 0.0) {
            msg.append("A altura do livro e requerido!");
        }

        if (b.getWidht() == 0.0 ) {
            msg.append("A largura do livro e requerido!");
        }

        if (b.getWeight() == 0.0) {
            msg.append("O peso do livro e requerido!");
        }

        if (b.getDepth()  == 0.0) {
            msg.append("A profundidade do livro e requerido!");
        }
        if (b.getListPrice()  == 0.0) {
            msg.append("O preco de venda do livro e requerido!");
        }
        if (b.getOurPrice()  == 0.0) {
            msg.append("O preco de custo e requerido!");
        }
        if (b.getPriceGroup().equals(null) || b.getPriceGroup().equals("")) {
            msg.append("O grupo de preco do livro e requerido!");
        }
        if (b.getInStockNumber() < 0) {
            msg.append("A quantidade de livros no esotque nap pode ser menor que 0!");
        }


        return msg.toString();
    }
}
