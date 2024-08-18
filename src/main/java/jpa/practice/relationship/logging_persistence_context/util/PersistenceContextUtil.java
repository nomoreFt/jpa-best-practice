package jpa.practice.relationship.logging_persistence_context.util;

import jakarta.persistence.EntityManager;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.util.Arrays;
import java.util.Map;

public class PersistenceContextUtil {

    public static void briefOverviewOfPersistentContextContent(EntityManager entityManager) {
        System.out.println("\n-----------------------------------------------------");

        PersistenceContext persistenceContext = getPersistenceContext(entityManager);

        int managedEntities = persistenceContext.getNumberOfManagedEntities();
        int collectionEntriesSize = persistenceContext.getCollectionEntriesSize();

        System.out.println("Total number of managed entities: " + managedEntities);
        System.out.println("Total number of collection entries: " + collectionEntriesSize);

        Map<EntityKey, Object> entitiesByKey = persistenceContext.getEntitiesByKey();

        if (!entitiesByKey.isEmpty()) {
            System.out.println("\nEntities by key:");
            entitiesByKey.forEach((key, value) -> System.out.println(key + ": " + value));

            System.out.println("\nStatus and hydrated state:");
            for (Object entry : entitiesByKey.values()) {
                EntityEntry ee = persistenceContext.getEntry(entry);
                System.out.println("Entity name: " + ee.getEntityName()
                        + " | Status: " + ee.getStatus()
                        + " | State: " + Arrays.toString(ee.getLoadedState()));
            }
        }

        if (collectionEntriesSize > 0) {
            System.out.println("\nCollection entries:");
            persistenceContext.forEachCollectionEntry(
                    (k, v) -> System.out.println("Key:" + k + ", Value:" + (v.getRole() == null ? "" : v)), false);
        }
        System.out.println("-----------------------------------------------------\n");
    }

    private static PersistenceContext getPersistenceContext(EntityManager entityManager) {
        SharedSessionContractImplementor sharedSession = entityManager.unwrap(SharedSessionContractImplementor.class);
        return sharedSession.getPersistenceContext();
    }
}
