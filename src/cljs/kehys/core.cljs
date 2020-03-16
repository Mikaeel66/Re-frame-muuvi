(ns kehys.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as rf]
   [kehys.events :as events]
   [kehys.views :as views]
   [kehys.config :as config]))



(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (rf/dispatch-sync [::events/initialize-db])
  (rf/dispatch [:get-api-data :categories "https://juu.azurewebsites.net/read_categories.php"])
  (dev-setup)
  (mount-root))
