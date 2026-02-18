#!/bin/bash

# ===== CONFIG =====
HOSTNAME="dynamic.bugboard26.it"
AUTH_USER="${DYN_USER}"
AUTH_PASS="${DYN_PASS}"
IP_SERVICE="https://ifconfig.me"
# ==================

# Ottieni IP pubblico con timeout e modalità silenziosa
IPADDR=$(curl -s --max-time 10 "$IP_SERVICE")

# Controllo che l'IP non sia vuoto
if [ -z "$IPADDR" ]; then
    echo "$(date) - ERRORE: impossibile ottenere IP pubblico" >> "$LOGFILE"
    exit 1
fi

echo $(date) - IP rilevato: $IPADDR

# Chiamata a OVH DynHost
RESULT=$(curl -s --max-time 30 -u "$AUTH_USER:$AUTH_PASS" \
        "https://www.ovh.com/nic/update?system=dyndns&hostname=$HOSTNAME&myip=$IPADDR")

# Log risultato
echo $(date) - Risposta OVH: $RESULT

# Controllo risposta
if [[ "$RESULT" == good* || "$RESULT" == nochg* ]]; then
    exit 0
else
    echo $(date) - ERRORE aggiornamento DynHost
    exit 1
fi

