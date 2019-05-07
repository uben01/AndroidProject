package utobe.learn2code.adapter;

import utobe.learn2code.enititymanager.EntityManager;

interface IAbstractAdapter {
    EntityManager entityManager = EntityManager.getInstance();
}
