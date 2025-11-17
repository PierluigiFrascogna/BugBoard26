#!/bin/bash

# Script per compilare BugBoard.tex e gestire i file di output.

# Imposta una variabile per il file principale per facilitare la manutenzione
MAIN_FILE="BugBoard"
BUILD_DIR="build"

# Crea la directory di build se non esiste
mkdir -p "$BUILD_DIR"

echo "Avvio compilazione di $MAIN_FILE.tex..."

# Esegui pdflatex la prima volta.
# --shell-escape è necessario per il pacchetto svg (inkscape).
# -output-directory salva i file generati nella cartella 'build'.
pdflatex --shell-escape -interaction=nonstopmode -output-directory="$BUILD_DIR" "$MAIN_FILE".tex

# Controlla se il primo pdflatex ha avuto successo
if [ $? -ne 0 ]; then
    echo "Errore durante la prima esecuzione di pdflatex. Interruzione."
    exit 1
fi

# Esegui makeglossaries all'interno della directory di build
echo "Creazione del glossario..."
(cd "$BUILD_DIR" && makeglossaries "$MAIN_FILE")

# Controlla se makeglossaries ha avuto successo
if [ $? -ne 0 ]; then
    echo "Errore durante l'esecuzione di makeglossaries. Interruzione."
    exit 1
fi

# Esegui pdflatex altre due volte per risolvere i riferimenti incrociati e il glossario
echo "Seconda esecuzione di pdflatex..."
pdflatex --shell-escape -interaction=nonstopmode -output-directory="$BUILD_DIR" "$MAIN_FILE".tex
if [ $? -ne 0 ]; then
    echo "Errore durante la seconda esecuzione di pdflatex. Interruzione."
    exit 1
fi

# Sposta il PDF finale nella directory corrente
if [ -f "$BUILD_DIR/$MAIN_FILE.pdf" ]; then
    mv "$BUILD_DIR/$MAIN_FILE.pdf" .
    echo "Compilazione completata con successo!"
    echo "Il file $MAIN_FILE.pdf è stato salvato nella directory corrente."
    echo "I file ausiliari si trovano nella directory '$BUILD_DIR'."
else
    echo "Errore: il file PDF non è stato trovato nella directory di build."
    exit 1
fi

exit 0
