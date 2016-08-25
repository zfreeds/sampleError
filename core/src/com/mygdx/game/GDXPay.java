package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.pay.Offer;
import com.badlogic.gdx.pay.OfferType;
import com.badlogic.gdx.pay.PurchaseManagerConfig;
import com.badlogic.gdx.pay.PurchaseObserver;
import com.badlogic.gdx.pay.PurchaseSystem;
import com.badlogic.gdx.pay.Transaction;
import com.mygdx.game.MyGdxGame;

/**
 * Created by zacharyfreedman on 2016-07-27.
 */
public class GDXPay {
    PurchaseManagerConfig config;
    MyGdxGame game;
   // public boolean implementationSuccessful=true;



    String base64EncodedKey="MY GOOGLE PLAYSTORE KEY";
    public GDXPay(final MyGdxGame game){
        this.game=game;


        try {
            PurchaseSystem.onAppRestarted();  //I added this line
            if(PurchaseSystem.hasManager()){
                System.out.println("PURCHASE SYSTEM HAS MANAGER: "+ PurchaseSystem.getManager());
                config = new PurchaseManagerConfig();
                config.addOffer(new Offer().setType(OfferType.ENTITLEMENT).setIdentifier("ExoticBundle"));

                config.addOffer(new Offer().setType(OfferType.ENTITLEMENT).setIdentifier("RemoveAds"));

                config.addOffer(new Offer().setType(OfferType.ENTITLEMENT).setIdentifier("Unlockables"));


                //Stores
                config.addStoreParam(PurchaseManagerConfig.STORE_NAME_ANDROID_GOOGLE, base64EncodedKey);

                config.addStoreParam(PurchaseManagerConfig.STORE_NAME_IOS_APPLE, base64EncodedKey); // <-- CHANGE KEY
                config.addStoreParam(PurchaseManagerConfig.STORE_NAME_DESKTOP_WINDOWS,base64EncodedKey);  //I added this one




                PurchaseSystem.install(new PurchaseObserver() {
                    @Override
                    public void handleInstall() {
                        System.out.println(" - purchase manager installed: " + PurchaseSystem.storeName() + ".\n");
                        // restore purchases
                        System.out.println(" - do a restore to check inventory\n");

                        //Execute this on a button instead!
                       // PurchaseSystem.purchaseRestore();

                    }

                    @Override
                    public void handleInstallError(Throwable e) {
                        System.out.println(" - error installing purchase manager: " + e + "\n");
                        e.printStackTrace();

                        // throw error
                       // throw new GdxRuntimeException(e);
                    }

                    @Override
                    public void handleRestore(Transaction[] transactions) {
                        // keep note of our purchases

                        System.out.println(" - totally " + transactions.length + " purchased products\n");
                        for (int i = 0; i < transactions.length; i++) {
                            if(transactions[i].getIdentifier().equals("RemoveAds")) {
//                                game.gsm.prefs.putBoolean("purchaseRemoveAds", true);
//                                game.gsm.purchaseRemoveAds=true;
//
//                                game.gsm.handler.disableAllAds();
//                                game.gsm.disableAds=true;
                            }else if(transactions[i].getIdentifier().equals("ExoticBundle")) {
//                                game.gsm.prefs.putBoolean("purchaseExoticBundle", true);
//                                game.gsm.purchaseExoticBundle=true;
                            }else if(transactions[i].getIdentifier().equals("Unlockables")) {
//                                game.gsm.prefs.putBoolean("purchaseUnlockables", true);
//                                game.gsm.purchaseUnlockables=true;
                            }
                        }
                       // game.gsm.prefs.flush();

                    }

                    @Override
                    public void handleRestoreError(Throwable e) {
                        System.out.println(" - error during purchase manager restore: " + e + "\n");

                        // throw error
                       // throw new GdxRuntimeException(e);
                    }

                    @Override
                    public void handlePurchase(Transaction transaction) {
                        System.out.println(" - purchased: " + transaction.getIdentifier() + "\n");

                        // dispose the purchase system
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run () {
                                System.out.println(" - disposing the purchase manager.\n");
                                PurchaseSystem.dispose();
                                System.out.println("Testing InApp System: COMPLETED\n");
                            }
                        });
                    }

                    @Override
                    public void handlePurchaseError(Throwable e) {
                        System.out.println(" - error purchasing: " + e + "\n");
                        // throw error
                        //throw new GdxRuntimeException(e);
                    }

                    @Override
                    public void handlePurchaseCanceled() {
                        System.out.println(" - purchase cancelled.\n");

                        // dispose the purchase system
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run () {
                                System.out.println(" - user canceled! - disposing the purchase manager.\n");
                                PurchaseSystem.dispose();
                                System.out.println("Testing InApp System: COMPLETED\n");
                            }
                        });
                    }
                },config);

            } else {
                //Toast an error
                System.out.println("NO PURCHASE SYSTEM MANAGER");

            }
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
           // implementationSuccessful=false;
        }


    }
    public void buyItem(String id){
        try{
            System.out.println(" - purchasing: " + id + ".\n");
            PurchaseSystem.purchase(id);
        }catch(Exception e){
            System.out.println("Failed to buy item: "+id+"\nError: ");
            e.printStackTrace();
        }

    }

    public void restorePurchases(){
        try {
            PurchaseSystem.purchaseRestore();
        } catch (Exception e) {
            System.out.println("Purchase Restore failed");
            e.printStackTrace();
        }catch (Throwable e){
            System.out.println("Purchase Restore failed");
            e.printStackTrace();

        }
    }
}
