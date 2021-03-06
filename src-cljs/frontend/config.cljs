(ns frontend.config)

(defn env
  "The name of the server configuration environment.
  For humans only: Do not gate features with this setting."
  []
  (aget js/window "renderContext" "env"))

(defn enterprise?
  "True if this is an enterprise (as opposed to main public web) deployment."
  []
  (boolean (aget js/window "renderContext" "enterprise")))

(defn pusher
  "Options to be passed to the Pusher client library."
  []
  ;;TODO Delete this if-let when https://github.com/circleci/circle/pull/3972 is fully deployed.
  (if-let [app-key (aget js/window "renderContext" "pusherAppKey")]
    {:key app-key}
    (js->clj (aget js/window "renderContext" "pusher"))))

(defn logging-enabled?
  []
  "If true, log statements print to the browswer's JavaScript console."
  (boolean (aget js/window "renderContext" "logging_enabled")))

(defn log-channels?
  "If true, log all messages on global core.async channels."
  []
  (boolean (aget js/window "renderContext" "log_channels")))

(defn assets-root
  "Path to root of CDN assets."
  []
  (aget js/window "renderContext" "assetsRoot"))

(defn github-endpoint
  "Full HTTP URL of GitHub API."
  []
  (aget js/window "renderContext" "githubHttpEndpoint"))

(defn stripe-key
  "Publishable key to identify our account with Stripe.
  See: https://stripe.com/docs/tutorials/dashboard#api-keys"
  []
  (aget js/window "renderContext" "stripePublishableKey"))

(defn analytics-enabled?
  []
  "If true, collect user analytics"
  ;; The value should be supplied by backend
  ;; till that happens, use enterprise? check instead
  (let [v (aget js/window "renderContext" "analytics_enabled")]
    (if-not (nil? v)
      (boolean v)
      ;; TODO: Kill this after backend populate the context value
      (not (enterprise?)))))
