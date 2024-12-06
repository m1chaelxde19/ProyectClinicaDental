document.getElementById('formCita').addEventListener('submit', async function (event) {
    event.preventDefault();

    const parametros = {
        fecha: document.getElementById('fecha').value,
        hora: document.getElementById('hora').value + ":00",
        paciente: document.getElementById('patientIdInput').value,
        odontologo: document.getElementById('odontologo').value,
        motivo: document.getElementById('selecMotivo').value,
        estado: document.getElementById('selecEstado').value,
        comentarios: document.getElementById('coment').value,
    }
    console.log("Cita: ", parametros);
    const respuesta = await fetch('/clinicaDental/odontologica/validar/cita', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(parametros)
    });

    const data = await respuesta.json();
    console.log("Data obtenida: ", data);

    const campos = {
        hora: { input: 'hora', error: 'errorHora' },
        fecha: { input: 'fecha', error: 'errorFecha' },
        odontologo: { input: 'odontologo', error: 'errorOdontologo' },
        motivo: { input: 'selecMotivo', error: 'errorMotivo' },
        estado: { input: 'selecEstado', error: 'errorEstado' },
        comentarios: { input: 'coment', error: 'errorComentarios' },
        paciente: { error: 'errorPaciente' } // Este campo no tiene un input
    };

    function limpiarCampos() {
        Object.keys(campos).forEach((campo) => {
            const config = campos[campo];
            const input = config.input ? document.getElementById(config.input) : null;
            const errorDiv = document.getElementById(config.error);

            if (input) input.classList.remove('is-invalid'); // Quita clase de error
            if (errorDiv) {
                errorDiv.textContent = ''; // Limpia mensaje de error
                errorDiv.style.display = 'none'; // Oculta el contenedor de error
            }
        });
    }

    limpiarCampos();

    for (const campo in data) {
        if (campos[campo]) {
            const config = campos[campo];
            const input = config.input ? document.getElementById(config.input) : null;
            const errorDiv = document.getElementById(config.error);

            if (input) {
                input.classList.add('is-invalid'); // Agregar clase Bootstrap para errores
            }

            if (errorDiv) {
                errorDiv.textContent = data[campo]; // Mostrar mensaje de error
                errorDiv.style.display = 'block';  // Hacer visible el contenedor de error
            }
        }
    }

    if (data.dataValid != null) {
        limpiarCampos();
        console.log("Horario válido: ", data);
        document.getElementById('idHorario').value = data.dataValid.idHorario;
        document.getElementById('horaAprox').value = data.dataValid.horaAproximada;
        alert("Cita creada correctamente");
        setTimeout(() => {
            this.submit();
        },1000);
    } else {
        console.log("Errores: ", data);
    }
});