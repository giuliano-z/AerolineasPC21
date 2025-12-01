#!/bin/bash

echo "🛫 Ejecutando Aerolíneas PC21..."
echo ""

cd "$(dirname "$0")"

# Compilar si es necesario
if [ ! -d "target/classes" ]; then
    echo "📦 Compilando proyecto..."
    mvn clean compile -q
fi

# Ejecutar la aplicación
java -cp target/classes com.aerolineaspc21.AerolineasApp
