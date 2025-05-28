from PySide6.QtWidgets import QApplication, QWidget
import sys

# Creamos la aplicación
app = QApplication(sys.argv)

# Leemos la hoja de estilo
with open("resources/style.qss", "r") as f:
    estilo = f.read()
    app.setStyleSheet(estilo)

# Creamos una ventana simple
ventana = QWidget()
ventana.setWindowTitle("Mi App")
ventana.resize(300, 200)
ventana.show()

# Ejecutamos
sys.exit(app.exec())

