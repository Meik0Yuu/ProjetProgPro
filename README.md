# ProjetProgPro
Projet jeu d'aventure console

## Aperçu
Dans ce projet, vous allez créer un jeu d’aventure textuel où les joueurs naviguent à travers différentes pièces, interagissent avec des objets, résolvent des énigmes et affrontent des ennemis. Le jeu devra être implémenté sous forme d’application console en Java, en utilisant les concepts de programmation orientée objet et des patrons de conception.

## Exigences

### Fonctionnalités du Jeu
1. Un monde contenant au moins **10 pièces/lieux interconnectés**
2. Des **objets** pouvant être ramassés, utilisés et combinés
3. Des **PNJ (Personnages Non Joueurs)** avec des dialogues basiques
4. Un **système de combat** avec au moins **2 types d’ennemis différents**
5. Une **gestion d’inventaire**
6. **Sauvegarde et chargement de l’état du jeu** (peut être trop compliqué le but serait de leur faire sauvegarder l'état du jeu dans un fichier et de pouvoir le charger)
7. Une **condition de victoire** (compléter une quête, vaincre un boss, etc.)

### Exigences Techniques

#### Concepts POO
1. **Agrégation** : Implémenter des relations où des objets contiennent d’autres objets (par exemple, des pièces contenant des objets, le joueur contenant un inventaire)
2. **Cohésion** : S’assurer que les classes ont des responsabilités bien définies et des méthodes liées
3. **Héritage** : Créer des hiérarchies de classes (par exemple, différents types d’objets, de personnages)
4. **Encapsulation** : Utiliser les modificateurs d’accès appropriés et les méthodes getter/setter
5. **Polymorphisme** : Implémenter la redéfinition de méthodes pour des comportements différents

#### Patrons de Conception
1. **Patron Factory** : Pour créer différents types d’objets du jeu (objets, ennemis, pièces)
2. **Patron Observer** : Pour les notifications d’événements (par exemple, changements de santé du joueur, ramassage d’objets)
3. **Patron State** : Pour gérer les états des personnages (par exemple, normal, empoisonné, renforcé)
4. **Patron Strategy** : Pour différentes stratégies de combat ou de déplacement
5. **Patron Singleton** : Pour un accès global à l’état du jeu ou aux gestionnaires de ressources
6. **Patron Facade** : Pour fournir une interface simplifiée aux sous-systèmes complexes du jeu

### Interface Utilisateur
- Interface textuelle dans la console
- Instructions claires pour les joueurs
- Système de commandes intuitif (par exemple : `aller nord`, `prendre clé`, `attaquer gobelin`)

## Livrables
1. Le code source du jeu
2. Un bref document expliquant :
    - Comment chaque concept POO a été implémenté
    - Comment chaque patron de conception a été utilisé
    - Les instructions pour jouer au jeu

