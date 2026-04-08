# Projet Logiciel
## Système de Gestion de Club Sportif

---

##  1. Présentation du projet
## 1.1 Introduction
Ce projet consiste à développer une application permettant la gestion complète d’un **club sportif**.

L’application est destinée aux :
- Administrateurs
- Assistants
- Adhérents

Elle permet de gérer :
- Les adhérents
- Les activités sportives
- Les paiements
- Les consultations personnelles

---

##  1.2. Objectifs

| Module | Fonctionnalités |
|--------|----------------|
|  Adhérents | Inscription, modification, archivage, restauration |
|  Activités | Ajouter, modifier, supprimer, afficher |
|  Paiements | Calcul du montant, validation, suivi |
|  Consultation | Profil adhérent, activités, état de paiement |

---

##  3. Conception UML

Le système a été modélisé à l’aide de plusieurs diagrammes :

###  Diagrammes réalisés :
- Diagramme de cas d’utilisation  
- Diagrammes de séquence  
- Diagrammes d’activité  

---

###  Exemples de scénarios

####  Consulter la liste des adhérents
- **Pré-condition** : utilisateur authentifié  
- **Post-condition** : affichage des adhérents avec leurs activités  

---

####  Gestion des activités
- Ajouter / Modifier / Supprimer une activité  
- Vérification des contraintes (adhérents inscrits)

---

####  Gestion des adhérents
- Inscription  
- Modification  
- Archivage / Restauration  

---

####  Gestion des paiements
- Calcul : frais de base + activités  
- Enregistrement du statut de paiement  

---

####  Consultation personnelle
- Accès au profil  
- Activités inscrites  
- État des paiements  

---

##  4. Technologies utilisées

-  Java  
-  Base de données (MySQL / SQLite)  
-  Interface graphique (JavaFX / Swing)  
-  Git & GitHub  

---

##  5. Fonctionnement du système

###  Acteurs
- Administrateur  
- Assistant  
- Adhérent  

###  Processus principaux
1. Authentification  
2. Gestion des données  
3. Traitement des paiements  
4. Consultation des informations  

---
## 6. Livrables

| N° | Livrable | Contenu attendu | 
|----|----------|----------------|
| 1 | Plan de projet | Méthode, planning, répartition des rôles, structure GitHub | 
| 2 | Analyse des besoins | Diagrammes de cas d'utilisation, spécifications fonctionnelles | 
| 3 | Document de conception | Diagramme de classes, MCD/MLD, architecture technique | 
| 4 | Code source | Code Java commenté, base de données, interface graphique fonctionnelle | 
| 5 | Rapport final | Étapes réalisées, problèmes rencontrés, solutions apportées | 
| 6 | Soutenance orale | Présentation et défense du projet (avant le 14 juin 2025) | 

##  7. Installation et exécution

```bash
git clone https://github.com/ton-user/projet.git
cd projet
