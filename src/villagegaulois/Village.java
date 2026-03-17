package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	
	private static class Marche {
		// Attributs de la classe Marche
		private Etal[] etals;
		
		// Constructeur de la classe Marche
		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		// Methode de la classe Marche
		private void utiliserEtal (int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if(indiceEtal >= 0 && indiceEtal < etals.length - 1) {
				etals[indiceEtal - 1 ].occuperEtal(vendeur, produit, nbProduit);
			}
			else {
				System.out.println("L'indice d'etal introuvable.\n");
			}
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalsAvecProduit = 0;
			
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtalsAvecProduit++;
				}
			}
			
			Etal[] etalsAvecProduit = new Etal[nbEtalsAvecProduit];
			int indexEtalAvecProduit = 0;
			
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsAvecProduit[indexEtalAvecProduit++] = etals[i];
				}
			}
			
			return etalsAvecProduit;
		}

		private Etal trouverVendeur (Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			
			return null;
		}
		
		private void afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;
					
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				}
				else {
					nbEtalVide++;
				}
			}
			
			chaine.append("Il reste " + nbEtalVide + " etals non utlises dans le marche.\n");
			
			System.out.println(chaine.toString());
		}
	}

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}