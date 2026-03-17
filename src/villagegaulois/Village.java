package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
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
			if(indiceEtal >= 0 && indiceEtal < etals.length) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
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

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
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
	
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");	
		int indexEtalLibre = marche.trouverEtalLibre();
		marche.utiliserEtal(indexEtalLibre, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indexEtalLibre + 1) + ".\n" );
		
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit (String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsAvecProduit = marche.trouverEtals(produit);
		
		if (etalsAvecProduit.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n");
		}
		else if (etalsAvecProduit.length == 1) {
			chaine.append("Seul le vendeur " + etalsAvecProduit[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
		}
		else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < etalsAvecProduit.length; i++) {
				chaine.append("- " + etalsAvecProduit[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal (Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
}