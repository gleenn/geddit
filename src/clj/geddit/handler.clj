(ns geddit.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [response resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [geddit.handlers.dongles :as dongles]))

(defroutes routes
           (GET "/" [] (resource-response "index.html" {:root "public"}))
           (GET "/news" [] (response "yo what's up"))
           (POST "/dongles" [params] (dongles/list-dongles params))
           (resources "/"))

(def dev-handler (-> #'routes wrap-reload))

(def handler routes)
