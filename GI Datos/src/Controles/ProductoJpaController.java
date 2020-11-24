/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Controles.exceptions.NonexistentEntityException;
import Entidades.Producto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.ProductoVenta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Home
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getProductoVenta() == null) {
            producto.setProductoVenta(new ArrayList<ProductoVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ProductoVenta> attachedProductoVenta = new ArrayList<ProductoVenta>();
            for (ProductoVenta productoVentaProductoVentaToAttach : producto.getProductoVenta()) {
                productoVentaProductoVentaToAttach = em.getReference(productoVentaProductoVentaToAttach.getClass(), productoVentaProductoVentaToAttach.getIdProductoVenta());
                attachedProductoVenta.add(productoVentaProductoVentaToAttach);
            }
            producto.setProductoVenta(attachedProductoVenta);
            em.persist(producto);
            for (ProductoVenta productoVentaProductoVenta : producto.getProductoVenta()) {
                Producto oldProductoOfProductoVentaProductoVenta = productoVentaProductoVenta.getProducto();
                productoVentaProductoVenta.setProducto(producto);
                productoVentaProductoVenta = em.merge(productoVentaProductoVenta);
                if (oldProductoOfProductoVentaProductoVenta != null) {
                    oldProductoOfProductoVentaProductoVenta.getProductoVenta().remove(productoVentaProductoVenta);
                    oldProductoOfProductoVentaProductoVenta = em.merge(oldProductoOfProductoVentaProductoVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            List<ProductoVenta> productoVentaOld = persistentProducto.getProductoVenta();
            List<ProductoVenta> productoVentaNew = producto.getProductoVenta();
            List<ProductoVenta> attachedProductoVentaNew = new ArrayList<ProductoVenta>();
            if(productoVentaNew!=null)
                for (ProductoVenta productoVentaNewProductoVentaToAttach : productoVentaNew) {
                    productoVentaNewProductoVentaToAttach = em.getReference(productoVentaNewProductoVentaToAttach.getClass(), productoVentaNewProductoVentaToAttach.getIdProductoVenta());
                    attachedProductoVentaNew.add(productoVentaNewProductoVentaToAttach);
                }
            productoVentaNew = attachedProductoVentaNew;
            producto.setProductoVenta(productoVentaNew);
            producto = em.merge(producto);
            if(productoVentaOld!=null)
                for (ProductoVenta productoVentaOldProductoVenta : productoVentaOld) {
                    if (!productoVentaNew.contains(productoVentaOldProductoVenta)) {
                        productoVentaOldProductoVenta.setProducto(null);
                        productoVentaOldProductoVenta = em.merge(productoVentaOldProductoVenta);
                    }
                }
            for (ProductoVenta productoVentaNewProductoVenta : productoVentaNew) {
                if (!productoVentaOld.contains(productoVentaNewProductoVenta)) {
                    Producto oldProductoOfProductoVentaNewProductoVenta = productoVentaNewProductoVenta.getProducto();
                    productoVentaNewProductoVenta.setProducto(producto);
                    productoVentaNewProductoVenta = em.merge(productoVentaNewProductoVenta);
                    if (oldProductoOfProductoVentaNewProductoVenta != null && !oldProductoOfProductoVentaNewProductoVenta.equals(producto)) {
                        oldProductoOfProductoVentaNewProductoVenta.getProductoVenta().remove(productoVentaNewProductoVenta);
                        oldProductoOfProductoVentaNewProductoVenta = em.merge(oldProductoOfProductoVentaNewProductoVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<ProductoVenta> productoVenta = producto.getProductoVenta();
            for (ProductoVenta productoVentaProductoVenta : productoVenta) {
                productoVentaProductoVenta.setProducto(null);
                productoVentaProductoVenta = em.merge(productoVentaProductoVenta);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Producto findProducto(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
