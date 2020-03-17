(ns kehys.events
  (:require
   [re-frame.core :as rf]
   [kehys.db :as db]
   [day8.re-frame.http-fx]
   [ajax.core :as ajax :refer [GET POST PUT]]))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(defn- isChecked[c_id]
     (-> js/document
       (.getElementById c_id)
       (.-checked)))
           ;(set! false)

(defn- lue_muuvit [a b]
  (rf/dispatch [:get-api-data :muuvi
      (str "https://juu.azurewebsites.net/filter_movies_by_category.php?title=" a "&flt=" b )]))

(rf/reg-event-db
  :value-change
   (fn [db [_ new-value]]
    (lue_muuvit new-value (get db :cate))
    (assoc db :val new-value)))

(rf/reg-event-db
    :valinta
    (fn [db _]   
      (let [huu
        (reduce 
          #(let [c_id (get %2 :category_id)]
            (str % (if (isChecked c_id)
                    (str (if (< 0 (count %)) ",") c_id )))) 
                      "" 
                      (get db :categories) )] 
                      (lue_muuvit (get db :val) huu)                      
                      (assoc db :cate huu))))
                      
(defn- make-api-call [resource on-success on-failure]
     {:http-xhrio {:method :get
                   :uri resource
                   :response-format (ajax/json-response-format {:keywords? true})
                   :on-success on-success
                   :on-failure on-failure}})
(rf/reg-event-fx
    :get-api-data
      (fn [{db :db} [_ key resource]]
         (make-api-call resource [:get-api-data-success key] [:get-api-data-failure key])))

(rf/reg-event-db
      :get-api-data-success
        (fn [db [_ key data]]
            (assoc db  key data)))

(rf/reg-event-db
      :get-api-data-failure
           (fn [db [_ k]]
             (prn (str "Failed to fetch " k))
             db))
