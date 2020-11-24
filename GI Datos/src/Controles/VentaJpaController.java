/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Controles.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Pago;
import java.util.ArrayList;
import java.util.List;
import Entidades.ProductoVenta;
import Entidades.Venta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Home
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) {
        if (venta.getPagos() == null) {
            venta.setPagos(new ArrayList<Pago>());
        }
        if (venta.getProductoVenta() == null) {
            venta.setProductoVenta(new ArrayList<ProductoVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pago> attachedPagos = new ArrayList<Pago>();
            for (Pago pagosPagoToAttach : venta.getPagos()) {
                pagosPagoToAttach = em.getReference(pagosPagoToAttach.getClass(), pagosPagoToAttach.getIdPago());
                attachedPagos.add(pagosPagoToAttach);
            }
            venta.setPagos(attachedPagos);
            List<ProductoVenta> attachedProductoVenta = new ArrayList<ProductoVenta>();
            for (ProductoVenta productoVentaProductoVentaToAttach : venta.getProductoVenta()) {
                productoVentaProductoVentaToAttach = em.getReference(productoVentaProductoVentaToAttach.getClass(), productoVentaProductoVentaToAttach.getIdProductoVenta());
                attachedProductoVenta.add(productoVentaProductoVentaToAttach);
            }
            venta.setProductoVenta(attachedProductoVenta);
            em.persist(venta);
            for (Pago pagosPago : venta.getPagos()) {
                Venta oldVentaOfPagosPago = pagosPago.getVenta();
                pagosPago.setVenta(venta);
                pagosPago = em.merge(pagosPago);
                if (oldVentaOfPagosPago != null) {
                    oldVentaOfPagosPago.getPagos().remove(pagosPago);
                    oldVentaOfPagosPago = em.merge(oldVentaOfPagosPago);
                }
            }
            for (ProductoVenta productoVentaProductoVenta : venta.getProductoVenta()) {
                Venta oldVentaOfProductoVentaProductoVenta = productoVentaProductoVenta.getVenta();
                productoVentaProductoVenta.setVenta(venta);
                productoVentaProductoVenta = em.merge(productoVentaProductoVenta);
                if (oldVentaOfProductoVentaProductoVenta != null) {
                    oldVentaOfProductoVentaProductoVenta.getProductoVenta().remove(productoVentaProductoVenta);
                    oldVentaOfProductoVentaProductoVenta = em.merge(oldVentaOfProductoVentaProductoVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta persistentVenta = em.find(Venta.class, venta.getIdVenta());
            List<Pago> pagosOld = persistentVenta.getPagos();
            List<Pago> pagosNew = venta.getPagos();
            List<ProductoVenta> productoVentaOld = persistentVenta.getProductoVenta();
            List<ProductoVenta> productoVentaNew = venta.getProductoVenta();
            List<Pago> attachedPagosNew = new ArrayList<Pago>();
            for (Pago pagosNewPagoToAttach : pagosNew) {
                pagosNewPagoToAttach = em.getReference(pagosNewPagoToAttach.getClass(), pagosNewPagoToAttach.getIdPago());
                attachedPagosNew.add(pagosNewPagoToAttach);
            }
            pagosNew = attachedPagosNew;
            venta.setPagos(pagosNew);
            List<ProductoVenta> attachedProductoVentaNew = new ArrayList<ProductoVenta>();
            for (ProductoVenta productoVentaNewProductoVentaToAttach : productoVentaNew) {
                productoVentaNewProductoVentaToAttach = em.getReference(productoVentaNewProductoVentaToAttach.getClass(), productoVentaNewProductoVentaToAttach.getIdProductoVenta());
                attachedProductoVentaNew.add(productoVentaNewProductoVentaToAttach);
            }
            productoVentaNew = attachedProductoVentaNew;
            venta.setProductoVenta(productoVentaNew);
            venta = em.merge(venta);
            for (Pago pagosOldPago : pagosOld) {
                if (!pagosNew.contains(pagosOldPago)) {
                    pagosOldPago.setVenta(null);
                    pagosOldPago = em.merge(pagosOldPago);
                }
            }
            for (Pago pagosNewPago : pagosNew) {
                if (!pagosOld.contains(pagosNewPago)) {
                    Venta oldVentaOfPagosNewPago = pagosNewPago.getVenta();
                    pagosNewPago.setVenta(venta);
                    pagosNewPago = em.merge(pagosNewPago);
                    if (oldVentaOfPagosNewPago != null && !oldVentaOfPagosNewPago.equals(venta)) {
                        oldVentaOfPagosNewPago.getPagos().remove(pagosNewPago);
                        oldVentaOfPagosNewPago = em.merge(oldVentaOfPagosNewPago);
                    }
                }
            }
            for (ProductoVenta productoVentaOldProductoVenta : productoVentaOld) {
                if (!productoVentaNew.contains(productoVentaOldProductoVenta)) {
                    productoVentaOldProductoVenta.setVenta(null);
                    productoVentaOldProductoVenta = em.merge(productoVentaOldProductoVenta);
                }
            }
            for (ProductoVenta productoVentaNewProductoVenta : productoVentaNew) {
                if (!productoVentaOld.contains(productoVentaNewProductoVenta)) {
                    Venta oldVentaOfProductoVentaNewProductoVenta = productoVentaNewProductoVenta.getVenta();
                    productoVentaNewProductoVenta.setVenta(venta);
                    productoVentaNewProductoVenta = em.merge(productoVentaNewProductoVenta);
                    if (oldVentaOfProductoVentaNewProductoVenta != null && !oldVentaOfProductoVentaNewProductoVenta.equals(venta)) {
                        oldVentaOfProductoVentaNewProductoVenta.getProductoVenta().remove(productoVentaNewProductoVenta);
                        oldVentaOfProductoVentaNewProductoVenta = em.merge(oldVentaOfProductoVentaNewProductoVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = venta.getIdVenta();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
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
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getIdVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            List<Pago> pagos = venta.getPagos();
            for (Pago pagosPago : pagos) {
                pagosPago.setVenta(null);
                pagosPago = em.merge(pagosPago);
            }
            List<ProductoVenta> productoVenta = venta.getProductoVenta();
            for (ProductoVenta productoVentaProductoVenta : productoVenta) {
                productoVentaProductoVenta.setVenta(null);
                productoVentaProductoVenta = em.merge(productoVentaProductoVenta);
            }
            em.remove(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
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

    public Venta findVenta(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
