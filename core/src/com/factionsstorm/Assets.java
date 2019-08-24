package com.factionsstorm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG=Assets.class.getName();
    public static final Assets instance = new Assets();

    public Village village;
    public Fixed fixed;
    public Menu menu;
    public Icon icon;

    /*public Campagne campagne;
    public Persos persos;
    public Combat combat;
    public Unites unites;
    public Anim anim;*/

    private AssetManager assetManager;

    public void onCreate(AssetManager assetManager){
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load("Village/Village", TextureAtlas.class);

        assetManager.load("Village/tile", TextureAtlas.class);

        assetManager.load("Batiments/Fixes/Fixe" ,TextureAtlas.class);
        assetManager.load("Batiments/Decors/Routes", TextureAtlas.class);
        assetManager.load("Menus/MenusTheme1/MenusTheme1" ,TextureAtlas.class);

        assetManager.load("Menu/square", TextureAtlas.class);
        assetManager.load("Menu/menu2", TextureAtlas.class);

        assetManager.load("Icons/Icons" ,TextureAtlas.class);


        /*assetManager.load("Menus/MenusPack" ,TextureAtlas.class);

        assetManager.load("Combat/Campagne/Campagne", TextureAtlas.class);
        assetManager.load("Persos/Persos", TextureAtlas.class);
        assetManager.load("Combat/Combat", TextureAtlas.class);
        assetManager.load("Unites/Unites", TextureAtlas.class);
        assetManager.load("Batiments/Anim/Anim" ,TextureAtlas.class);*/

        assetManager.finishLoading();
        Gdx.app.debug (TAG, "# of assets loaded: "+assetManager.getAssetNames().size);
        for (String a: assetManager.getAssetNames())
            Gdx.app.debug(TAG, "Asset: "+a);

        /*TextureAtlas atlasCampagne = assetManager.get("Combat/Campagne/Campagne");
        TextureAtlas atlasPersos = assetManager.get("Persos/Persos");
        TextureAtlas atlasCombat = assetManager.get("Combat/Combat");
        TextureAtlas atlasUnites = assetManager.get("Unites/Unites");
        TextureAtlas atlasAnim = assetManager.get("Batiments/Anim/Anim");

        for(Texture t : atlasCampagne.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}
        for(Texture t : atlasPersos.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}
        for(Texture t : atlasCombat.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}
        for(Texture t : atlasUnites.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}
        for(Texture t : atlasAnim.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}*/

        village = new Village();
        fixed = new Fixed();
        menu = new Menu();
        icon = new Icon();

        /*campagne = new Campagne(atlasCampagne);
        persos = new Persos(atlasPersos);
        combat = new Combat(atlasCombat);
        unites = new Unites(atlasUnites);
        anim = new Anim(atlasAnim);*/
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    public class Village{
        public final TextureRegion village, VagueG[] = new TextureRegion[4], VagueD[] = new TextureRegion[4],
                extensionC, extensionD, extensionG,tile;
        public Village(){
            TextureAtlas atlas = assetManager.get("Village/Village");

            for(Texture t : atlas.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}

            village = atlas.findRegion("mapIle3");
            extensionC = atlas.findRegion("extensionC");
            extensionD = atlas.findRegion("extensioncoteD");
            extensionG = atlas.findRegion("extensioncoteG");
            VagueG[0] = atlas.findRegion("G2");
            VagueG[1] = atlas.findRegion("G3");
            VagueG[2] = atlas.findRegion("G6");
            VagueG[3] = atlas.findRegion("G7");
            VagueD[0] = atlas.findRegion("D1");
            VagueD[1] = atlas.findRegion("D3");
            VagueD[2] = atlas.findRegion("D6");
            VagueD[3] = atlas.findRegion("D8");

            //TODO fusionner les atlas

            atlas = assetManager.get("Village/tile");
            for(Texture t : atlas.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}
            tile = atlas.findRegion("Tile");
        }
    }

    public class Fixed{
        public final TextureRegion HDVlv1,HDVlv2,
                hutte1,hutte2,hutte3,pilotis1,pilotis2,pilotis3,chalet1,chalet2,chalet3,briques1,briques2,briques3,villa1,villa2,villa3,
                ferme,scierie1,scierie2,scierie3,derrick1,derrick2,derrick3,
                mineFer1,mineFer2,mineFer3,mineCu1,mineCu2,mineCu3,mineAlu1,mineAlu2,mineAlu3,mineOr1,mineOr2,mineOr3,mineUra1,mineUra2,mineUra3,plasma,
                caserne1,caserne2,caserne3,port1,port1Reflet,port2,port2Reflet,port3,port3Reflet,baseAerienne1,baseAerienne2,baseAerienne3,casernePlasma,portPlasma,portPlasmaReflet,baseAeriennePlasma,
                reserveEnergie,garnison1,garnison2,garnison3,labo1,labo2,labo3,QGRessources1,QGRessources2,QGRessources3,QGMilitaire1,QGMilitaire2,QGMilitaire3,marche,
                chantier2,chantier3;
        public final TextureRegion route0,route1B,route1G,route1H,route1D,route2BG,route2HG,route2HD,route2BD,route2HB,route2GD,route3HGD,route3DHB,route3BGD,route3GHB,route4;

        public Fixed(){
            TextureAtlas atlas = assetManager.get("Batiments/Fixes/Fixe");
            TextureAtlas atlasRoute = assetManager.get("Batiments/Decors/Routes");

            for(Texture t : atlasRoute.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}
            for(Texture t : atlas.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}

            hutte1 = atlas.findRegion("Huttelv1");
            hutte2 = atlas.findRegion("Huttelv2");
            hutte3 = atlas.findRegion("Huttelv3");
            pilotis1 = atlas.findRegion("Pilotislv1");
            pilotis2 = atlas.findRegion("Pilotislv2");
            pilotis3 = atlas.findRegion("Pilotislv3");
            chalet1 = atlas.findRegion("Chaletlv1");
            chalet2 = atlas.findRegion("Chaletlv2");
            chalet3 = atlas.findRegion("Chaletlv3");
            briques1 = atlas.findRegion("Briqueslv1");
            briques2 = atlas.findRegion("Briqueslv2");
            briques3 = atlas.findRegion("Briqueslv3");
            villa1 = atlas.findRegion("Villalv1");
            villa2 = atlas.findRegion("Villalv2");
            villa3 = atlas.findRegion("Villalv3");

            ferme = atlas.findRegion("Ferme");
            scierie1 = atlas.findRegion("Scierielv1");
            scierie2 = atlas.findRegion("Scierielv2");
            scierie3 = atlas.findRegion("Scierielv3");
            derrick1 = atlas.findRegion("Dericklv1");
            derrick2 = atlas.findRegion("Dericklv2");
            derrick3 = atlas.findRegion("Dericklv3");
            mineFer1 = atlas.findRegion("MineFerlv1");
            mineFer2 = atlas.findRegion("MineFerlv2");
            mineFer3 = atlas.findRegion("MineFerlv3");
            mineCu1 = atlas.findRegion("MineCulv1");
            mineCu2 = atlas.findRegion("MineCulv2");
            mineCu3 = atlas.findRegion("MineCulv3");
            mineAlu1 = atlas.findRegion("MineAlulv1");
            mineAlu2 = atlas.findRegion("MineAlulv2");
            mineAlu3 = atlas.findRegion("MineAlulv3");
            mineOr1 = atlas.findRegion("MineOrlv1");
            mineOr2 = atlas.findRegion("MineOrlv2");
            mineOr3 = atlas.findRegion("MineOrlv3");
            mineUra1 = atlas.findRegion("MineUralv1");
            mineUra2 = atlas.findRegion("MineUralv2");
            mineUra3 = atlas.findRegion("MineUralv3");
            plasma = atlas.findRegion("Plasma");

            caserne1 = atlas.findRegion("Casernelv1");
            caserne2 = atlas.findRegion("Casernelv2");
            caserne3 = atlas.findRegion("Casernelv3");
            port1 = atlas.findRegion("Portlv1");
            port1Reflet = atlas.findRegion("Portlv1refletseul");
            port2 = atlas.findRegion("Portlv2");
            port2Reflet = atlas.findRegion("Portlv2refletseul");
            port3 = atlas.findRegion("Portlv3");
            port3Reflet = atlas.findRegion("Portlv3refletseul");
            baseAerienne1 = atlas.findRegion("BaseAeriennelv1");
            baseAerienne2 = atlas.findRegion("BaseAeriennelv2");
            baseAerienne3 = atlas.findRegion("BaseAeriennelv3");
            casernePlasma = atlas.findRegion("CasernePlasma");
            portPlasma = atlas.findRegion("PortPlasma");
            portPlasmaReflet = atlas.findRegion("PortPlasmarefletseul");
            baseAeriennePlasma = atlas.findRegion("BaseAeriennePlasma");

            reserveEnergie = atlas.findRegion("ReserveEnergie");
            garnison1 = atlas.findRegion("Garnisonlv1");
            garnison2 = atlas.findRegion("Garnisonlv2");
            garnison3 = atlas.findRegion("Garnisonlv3");
            labo1 = atlas.findRegion("Laboratoirelv1");
            labo2 = atlas.findRegion("Laboratoirelv2");
            labo3 = atlas.findRegion("Laboratoirelv3");
            QGRessources1 = atlas.findRegion("QGRessourceslv1");
            QGRessources2 = atlas.findRegion("QGRessourceslv2");
            QGRessources3 = atlas.findRegion("QGRessourceslv3");
            QGMilitaire1 = atlas.findRegion("GQMilitairelv1");
            QGMilitaire2 = atlas.findRegion("GQMilitairelv2");
            QGMilitaire3 = atlas.findRegion("GQMilitairelv3");
            marche = atlas.findRegion("Marche");

            HDVlv1 = atlas.findRegion("HDVlv1");
            HDVlv2 = atlas.findRegion("HDVlv2");

            chantier2 = atlas.findRegion("Chantier2");
            chantier3 = atlas.findRegion("Chantier3");

            route0 = atlasRoute.findRegion("Route0");
            route1B = atlasRoute.findRegion("Route1B");
            route1G = atlasRoute.findRegion("Route1G");
            route1H = atlasRoute.findRegion("Route1H");
            route1D = atlasRoute.findRegion("Route1D");
            route2BG = atlasRoute.findRegion("Route2BG");
            route2HG = atlasRoute.findRegion("Route2HG");
            route2HD = atlasRoute.findRegion("Route2HD");
            route2BD = atlasRoute.findRegion("Route2BD");
            route2HB = atlasRoute.findRegion("Route2HB");
            route2GD = atlasRoute.findRegion("Route2GD");
            route3HGD = atlasRoute.findRegion("Route3HGD");
            route3DHB = atlasRoute.findRegion("Route3DHB");
            route3BGD = atlasRoute.findRegion("Route3BGD");
            route3GHB = atlasRoute.findRegion("Route3GHB");
            route4 = atlasRoute.findRegion("Route4");
        }
    }

    public class Menu{
        public final TextureRegion square, topRightTriangle, topLeftTriangle, bottomLeftTriangle, bottomRightTriangle, fleche, carreExp, carreEnergie, button, smallButton, buttonML, barreC, barreHaut, barreBas, barreBasFull, buttonRetour, buttonValider, buttonBack, buttonInfo, buttonSend, buttonRessources, buttonProduction, barreExp, barreChargement, fond,
                buttonVert, buttonRouge, fenetreFull, fenetreValidation, fenetreSlide0, fenetreSlide1, flecheD, flecheG, buttonQuitterSeul, buttonValiderSeul,graphMarche;
        public Menu(){
            TextureAtlas atlas = assetManager.get("Menus/MenusTheme1/MenusTheme1");

            for(Texture t : atlas.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}

            carreExp = atlas.findRegion("carreBleu");
            carreEnergie = atlas.findRegion("carreJaune");
            button = atlas.findRegion("bouton");
            smallButton = atlas.findRegion("smallBouton");
            buttonML = atlas.findRegion("boutonML");
            barreC = atlas.findRegion("barreC");
            barreHaut = atlas.findRegion("barreHaut");
            barreBas = atlas.findRegion("barreBas");
            barreBasFull = atlas.findRegion("barreBasFull");
            buttonRetour = atlas.findRegion("boutonQuitter");
            buttonValider = atlas.findRegion("boutonValider");
            buttonBack = atlas.findRegion("boutonRetour");
            buttonInfo = atlas.findRegion("boutonInfo");
            buttonSend = atlas.findRegion("boutonSend");
            buttonRessources = atlas.findRegion("boutonRessources");
            barreExp = atlas.findRegion("barreExp");
            barreChargement = atlas.findRegion("barreChargement");
            buttonProduction = atlas.findRegion("boutonProduction");
            buttonVert = atlas.findRegion("boutonVert");
            buttonRouge = atlas.findRegion("boutonRouge");
            fond = atlas.findRegion("fond");
            fenetreFull = atlas.findRegion("fenetreFull");
            fenetreValidation = atlas.findRegion("fenetreValidation");
            fenetreSlide0 = atlas.findRegion("fenetre1");
            fenetreSlide1 = atlas.findRegion("fenetre0");
            flecheD = atlas.findRegion("flecheD");
            flecheG = atlas.findRegion("flecheG");
            buttonQuitterSeul = atlas.findRegion("boutonQuitterSeul");
            buttonValiderSeul = atlas.findRegion("boutonValiderSeul");
            graphMarche = atlas.findRegion("graphMarche");

            atlas = assetManager.get("Menu/square");

            for(Texture t : atlas.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}

            square = atlas.findRegion("square");

            atlas = assetManager.get("Menu/menu2");

            //for(Texture t : atlas.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}

            fleche = atlas.findRegion("Flèche");
            topRightTriangle = atlas.findRegion("triangleBlanc1");
            topLeftTriangle = atlas.findRegion("triangleBlanc2");
            bottomLeftTriangle = atlas.findRegion("triangleBlanc3");
            bottomRightTriangle = atlas.findRegion("triangleBlanc4");
        }
    }

    public class Icon{
        public final TextureRegion ressources[] = new TextureRegion[10],iProd[] = new TextureRegion[8],unite[][][] = new TextureRegion[3][3][2];
        public final TextureRegion buttonAttack, buttonShop, podium, fleches, message, parametre, trophe, energie, exp, iconTrophe,z,epee,bouclier,cible,troupe,
                build,delete,tile;
        public Icon(){
            TextureAtlas atlas = assetManager.get("Icons/Icons");

            for(Texture t : atlas.getTextures()){t.setFilter(TextureFilter.Linear, TextureFilter.Linear);}

            ressources[0] = atlas.findRegion("factionCoin");
            ressources[1] = atlas.findRegion("buche");
            ressources[2] = atlas.findRegion("baril");
            ressources[3] = atlas.findRegion("lingot0");
            ressources[4] = atlas.findRegion("lingot1");
            ressources[5] = atlas.findRegion("lingot2");
            ressources[6] = atlas.findRegion("lingot3");
            ressources[7] = atlas.findRegion("lingot4");
            ressources[8] = atlas.findRegion("plasma");
            ressources[9] = atlas.findRegion("rubis");
            iProd[0] = atlas.findRegion("iProd1");
            iProd[1] = atlas.findRegion("iProd2");
            iProd[2] = atlas.findRegion("iProd3");
            iProd[3] = atlas.findRegion("iProd4");
            iProd[4] = atlas.findRegion("iProd5");
            iProd[5] = atlas.findRegion("iProd6");
            iProd[6] = atlas.findRegion("iProd7");
            iProd[7] = atlas.findRegion("iProd8");
            buttonAttack = atlas.findRegion("boutonAttaquer");
            buttonShop = atlas.findRegion("boutonShop");
            podium = atlas.findRegion("podium");
            fleches = atlas.findRegion("fleches");
            message = atlas.findRegion("message");
            parametre = atlas.findRegion("parametre");
            trophe = atlas.findRegion("trophe");
            energie = atlas.findRegion("eclair");
            exp = atlas.findRegion("etoile");
            iconTrophe = atlas.findRegion("iconTrophe");
            z = atlas.findRegion("z");
            epee = atlas.findRegion("epee");
            bouclier = atlas.findRegion("bouclier");
            cible = atlas.findRegion("cible");
            troupe = atlas.findRegion("troupe");
            build = atlas.findRegion("build");
            delete = atlas.findRegion("delete");
            tile = atlas.findRegion("Tile");
            unite[0][0][0] = atlas.findRegion("infanterie2");
            unite[0][0][1] = atlas.findRegion("infanterie");
            unite[0][1][0] = atlas.findRegion("canons2");
            unite[0][1][1] = atlas.findRegion("canons");
            unite[0][2][0] = atlas.findRegion("char2");
            unite[0][2][1] = atlas.findRegion("char");
            unite[1][0][0] = atlas.findRegion("sousmarin2");
            unite[1][0][1] = atlas.findRegion("sousmarin");
            unite[1][1][0] = atlas.findRegion("cuirasse2");
            unite[1][1][1] = atlas.findRegion("cuirasse");
            unite[1][2][0] = atlas.findRegion("porteavion2");
            unite[1][2][1] = atlas.findRegion("porteavion");
            unite[2][0][0] = atlas.findRegion("chasseur2");
            unite[2][0][1] = atlas.findRegion("chasseur");
            unite[2][1][0] = atlas.findRegion("helico2");
            unite[2][1][1] = atlas.findRegion("helico");
            unite[2][2][0] = atlas.findRegion("bombardier2");
            unite[2][2][1] = atlas.findRegion("bombardier");
        }
    }

    /*public class Campagne{
        public final TextureRegion ile[] = new TextureRegion[20],mapCampagne, drapeau, degrade,terrain[]=new TextureRegion[3];
        public Campagne(TextureAtlas atlas){
            for(int z=0; z<20; z++){
                ile[z] = atlas.findRegion("ile-"+(z+1));
            }
            mapCampagne = atlas.findRegion("mapCampagne");
            drapeau = atlas.findRegion("drapeau");
            degrade = atlas.findRegion("degrade");
            terrain[0] = atlas.findRegion("terre");
            terrain[1] = atlas.findRegion("mer");
            terrain[2] = atlas.findRegion("ciel");
        }
    }

    public class Persos{
        public final TextureRegion perso1;
        public Persos(TextureAtlas atlas){
            perso1 = atlas.findRegion("personnage 1 (512x512)");
        }
    }

    public class Combat{
        public final TextureRegion decors[] = new TextureRegion[3],vagues[] = new TextureRegion[7],nuages[] = new TextureRegion[3],nuageFond,barre,
                boost[] = new TextureRegion[6],herbe,elementTerre[] = new TextureRegion[10];
        public final Animation tir,rafale,impact,impactLourd;
        public Combat(TextureAtlas atlas){
            decors[0] = atlas.findRegion("decorTerre");
            decors[1] = atlas.findRegion("decorMer");
            decors[2] = atlas.findRegion("decorCiel");
            herbe = atlas.findRegion("herbe");
            elementTerre[0] = atlas.findRegion("ele0");
            elementTerre[1] = atlas.findRegion("ele1");
            elementTerre[2] = atlas.findRegion("ele2");
            elementTerre[3] = atlas.findRegion("ele3");
            elementTerre[4] = atlas.findRegion("ele4");
            elementTerre[5] = atlas.findRegion("ele5");
            elementTerre[6] = atlas.findRegion("ele6");
            elementTerre[7] = atlas.findRegion("ele7");
            elementTerre[8] = atlas.findRegion("ele8");
            elementTerre[9] = atlas.findRegion("ele9");
            vagues[0] = atlas.findRegion("vague1");
            vagues[1] = atlas.findRegion("vague2");
            vagues[2] = atlas.findRegion("vague3");
            vagues[3] = atlas.findRegion("vague4");
            vagues[4] = atlas.findRegion("vague5");
            vagues[5] = atlas.findRegion("vague6");
            vagues[6] = atlas.findRegion("vague7");
            nuages[0] = atlas.findRegion("nuage1");
            nuages[1] = atlas.findRegion("nuage5");
            nuages[2] = atlas.findRegion("nuage7");
            nuageFond = atlas.findRegion("nuageFond");
            barre = atlas.findRegion("barre");
            boost[0] = atlas.findRegion("heal");
            boost[1] = atlas.findRegion("missile");
            boost[2] = atlas.findRegion("bombardement");
            boost[3] = atlas.findRegion("cem");
            boost[4] = atlas.findRegion("poison");
            boost[5] = atlas.findRegion("nuclear");

            Array<AtlasRegion> regions = atlas.findRegions("tir");
            tir = new Animation(0.05f,regions,Animation.PlayMode.NORMAL);
            rafale = new Animation(0.05f,regions,Animation.PlayMode.LOOP);

            regions = atlas.findRegions("impact");
            impact = new Animation(0.05f,regions,Animation.PlayMode.LOOP);
            impactLourd = new Animation(0.1f,regions,Animation.PlayMode.LOOP);
        }
    }
    public class Unites{
        public final TextureRegion unite[][][] = new TextureRegion[3][3][9];
        public Unites(TextureAtlas atlas){
            unite[2][0][0] = atlas.findRegion("Anitron");
            unite[2][1][0] = atlas.findRegion("Spec");
            unite[2][2][0] = atlas.findRegion("Bomber");
        }
    }
    public class Anim{
        public final Animation animScierieLv1,animScierieLv2,animScierieLv3,animDerrickLv1,animDerrickLv2,animDerrickLv3,animMineFerLv1,animMineCuLv1,animMineAluLv1,
                animMineOrLv1,animMineUraLv1,animMineFerLv2,animMineCuLv2,animMineAluLv2,animMineOrLv2,animMineUraLv2,animMineFerLv3,animMineCuLv3,animMineAluLv3,
                animMineOrLv3,animMineUraLv3,animPlasmaLv1,
                animCaserneLv1,animCaserneLv2,animCaserneLv3,animCasernePlasma,
                animBaseAerienneLv1,animBaseAerienneLv2,animBaseAerienneLv3,animBaseAeriennePlasma,
                animReserveEnergieLv1;
        AtlasRegion imageDebut, image1, image2;
        public final TextureRegion ferme1,ferme2,ferme3;
        double rdm;
        public Anim(TextureAtlas atlas){
            ferme1 = atlas.findRegion("Ferme1");
            ferme2 = atlas.findRegion("Ferme2");
            ferme3 = atlas.findRegion("Ferme3");

            Array<AtlasRegion> regions = atlas.findRegions("ScierieLv1");
            imageDebut=regions.first();
            for(int i=0;i<8;i++){
                regions.insert(0, imageDebut);
            }
            animScierieLv1 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);

            regions = atlas.findRegions("ScierieLv2");
            imageDebut=regions.first();
            for(int i=0;i<8;i++){
                regions.insert(0, imageDebut);
            }
            animScierieLv2 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);

            regions = atlas.findRegions("ScierieLv3");
            imageDebut=regions.first();
            for(int i=0;i<8;i++){
                regions.insert(0, imageDebut);
            }
            animScierieLv3 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);

            regions = atlas.findRegions("DerickLv1");
            animDerrickLv1 = new Animation(0.12f,regions,Animation.PlayMode.LOOP_PINGPONG);
            regions = atlas.findRegions("DerickLv2");
            animDerrickLv2 = new Animation(0.12f,regions,Animation.PlayMode.LOOP_PINGPONG);
            regions = atlas.findRegions("DerickLv3");
            animDerrickLv3 = new Animation(0.12f,regions,Animation.PlayMode.LOOP_PINGPONG);
            regions = atlas.findRegions("MineFerLv1");
            animMineFerLv1 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineCuLv1");
            animMineCuLv1 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineAluLv1");
            animMineAluLv1 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineOrLv1");
            animMineOrLv1 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineUraLv1");
            animMineUraLv1 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineFerLv2");
            animMineFerLv2 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineCuLv2");
            animMineCuLv2 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineAluLv2");
            animMineAluLv2 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineOrLv2");
            animMineOrLv2 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineUraLv2");
            animMineUraLv2 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineFerLv3");
            animMineFerLv3 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineCuLv3");
            animMineCuLv3 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineAluLv3");
            animMineAluLv3 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineOrLv3");
            animMineOrLv3 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("MineUraLv3");
            animMineUraLv3 = new Animation(0.15f,regions,Animation.PlayMode.LOOP);
            regions = atlas.findRegions("Plasma");
            animPlasmaLv1 = new Animation(0.15f,regions,Animation.PlayMode.LOOP_PINGPONG);
            regions = atlas.findRegions("ReserveEnergie");
            animReserveEnergieLv1 = new Animation(0.12f,regions,Animation.PlayMode.LOOP_PINGPONG);


            regions = atlas.findRegions("CaserneLv1");
            image1=regions.first();
            image2=regions.get(1);
            for(int i=0;i<50;i++){
                rdm = Math.random();
                if(rdm<0.5f){regions.insert(0, image1);}
                else{regions.insert(0, image2);}
            }
            animCaserneLv1 = new Animation(0.1f,regions,Animation.PlayMode.LOOP);

            regions = atlas.findRegions("CaserneLv2");
            image1=regions.first();
            image2=regions.get(1);
            for(int i=0;i<50;i++){
                rdm = Math.random();
                if(rdm<0.5f){regions.insert(0, image1);}
                else{regions.insert(0, image2);}
            }
            animCaserneLv2 = new Animation(0.1f,regions,Animation.PlayMode.LOOP);

            regions = atlas.findRegions("CaserneLv3");
            image1=regions.first();
            image2=regions.get(1);
            for(int i=0;i<50;i++){
                rdm = Math.random();
                if(rdm<0.5f){regions.insert(0, image1);}
                else{regions.insert(0, image2);}
            }
            animCaserneLv3 = new Animation(0.1f,regions,Animation.PlayMode.LOOP);

            regions = atlas.findRegions("CasernePlasma");
            animCasernePlasma = new Animation(0.15f,regions,Animation.PlayMode.LOOP_PINGPONG);

            regions = atlas.findRegions("BaseAerienneLv1");
            image1=regions.first();
            image2=regions.get(1);
            for(int i=0;i<50;i++){
                rdm = Math.random();
                if(rdm<0.5f){regions.insert(0, image1);}
                else{regions.insert(0, image2);}
            }
            animBaseAerienneLv1 = new Animation(0.1f,regions,Animation.PlayMode.LOOP);

            regions = atlas.findRegions("BaseAerienneLv2");
            image1=regions.first();
            image2=regions.get(1);
            for(int i=0;i<50;i++){
                rdm = Math.random();
                if(rdm<0.5f){regions.insert(0, image1);}
                else{regions.insert(0, image2);}
            }
            animBaseAerienneLv2 = new Animation(0.1f,regions,Animation.PlayMode.LOOP);

            regions = atlas.findRegions("BaseAerienneLv3");
            image1=regions.first();
            image2=regions.get(1);
            for(int i=0;i<50;i++){
                rdm = Math.random();
                if(rdm<0.5f){regions.insert(0, image1);}
                else{regions.insert(0, image2);}
            }
            animBaseAerienneLv3 = new Animation(0.1f,regions,Animation.PlayMode.LOOP);

            regions = atlas.findRegions("BaseAeriennePlasma");
            animBaseAeriennePlasma = new Animation(0.15f,regions,Animation.PlayMode.LOOP_PINGPONG);
        }
    }*/
}


