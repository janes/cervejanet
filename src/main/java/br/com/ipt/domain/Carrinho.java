package br.com.ipt.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Employee entity.                                                        
 * 
 */
@ApiModel(description = ""
    + "The Employee entity.                                                   "
    + "")
@Entity
@Table(name = "carrinho")
public class Carrinho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cliente_id")
    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Carrinho carrinho = (Carrinho) o;
        if(carrinho.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, carrinho.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Carrinho{" +
            "id=" + id +
            ", clienteId='" + clienteId + "'" +
            '}';
    }
}
