const camposCita = {
    hora: { input: 'nuevahora', error: 'horaError' },
    fecha: { input: 'nuevaFecha', error: 'fechaError' },
    odontologo: { input: 'Eodontologo', error: 'odontoError' },
    motivo: { input: 'EselecMotivo', error: 'motivoError' },
    estado: { input: 'Edestado', error: 'estadoError' },
    comentarios: { input: 'nuevocoment', error: 'comentError' },
    horaFin: { input: 'nueHoraFin', error: 'horaErrorFin' },
    paciente: { error: 'errorPaciente' } // Este campo no tiene un input
};
function editarCita(idCita) {
    // Seleccionar todos los botones de edición

    limpiarCamposCita(camposCita);
    fetch(`/clinicaDental/odontologica/cita/EditCita/${idCita}`)
        .then(response => {
           if (!response.ok) {
               throw new Error('Error al obtener los datos de la cita');
           }
           return response.json();
        }).then(data =>{
            console.log(data);
            document.getElementById('idPacienteEd').value=data.data.idPaciente;
            document.getElementById('ed_citaId').value=data.data.idCita;
            document.getElementById('nuevaFecha').value=data.data.fecha;
            document.getElementById('nuevahora').value=data.data.hora;
            document.getElementById('EselecMotivo').value=data.data.motivo;
            document.getElementById('Edestado').value=data.data.estado;
            document.getElementById('Eodontologo').value=data.data.idOdontologo;
            document.getElementById('nuevocoment').textContent=data.data.comentarios;
            document.getElementById('horarioId').value=data.data.idHorario;
            document.getElementById('selecPatient').textContent=data.data.nombrePaciente;
            document.getElementById('nueHoraFin').value=data.data.horaFin;
        }).catch(error => {
            console.error('Error:', error);
        });
    alert("Editar cita con ID: " + idCita);
}

document.getElementById('formEdit').addEventListener('submit', async function (event){
    event.preventDefault();

    const cita = document.getElementById('ed_citaId').value;
    const fecha= document.getElementById('nuevaFecha').value;
    const hora= document.getElementById('nuevahora').value;
    const paciente = document.getElementById('idPacienteEd').value;
    const odontologo = document.getElementById('Eodontologo').value;
    const motivo = document.getElementById('EselecMotivo').value;
    const estado = document.getElementById('Edestado').value;
    const comentarios = document.getElementById('nuevocoment').value;
    const idHorario = document.getElementById('horarioId').value;
    const horaFin = document.getElementById('nueHoraFin').value;

    const parametrosValidar = {
        idCita:cita,
        fecha: fecha,
        hora: hora,
        horaFin: horaFin,
        paciente: paciente,
        odontologo: odontologo,
        motivo: motivo,
        estado: estado,
        comentarios: comentarios,
        horario: idHorario
    }

    console.log("Cita a enviar: ", parametrosValidar);
    const respuesta = await fetch(`/clinicaDental/odontologica/validar/citaEditValid`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(parametrosValidar)
    });

    const data = await respuesta.json();

    console.log("Data obtenida: ", data);

    limpiarCamposCita(camposCita);

    for (const campo in data) {
        if (camposCita[campo]) {
            const configu = camposCita[campo];
            const input = configu.input ? document.getElementById(configu.input) : null;
            const errorDiv = document.getElementById(configu.error);

            if (input) {
                input.classList.add('is-invalid'); // Agregar clase Bootstrap para errores
            }

            if (errorDiv) {
                errorDiv.textContent = data[campo]; // Mostrar mensaje de error
                errorDiv.style.display = 'block';  // Hacer visible el contenedor de error
            }
        }
    }

    if (data.dataValidada != null) {
        limpiarCamposCita(camposCita);
        console.log("Horario válido: ", data);
        alert("Cita editada correctamente");
        document.getElementById('nueHoraFin').value = data.dataValidada.horaAproximada;
        document.getElementById('horarioId').value = data.dataValidada.idHorario;
        this.submit();
    } else {
        document.getElementById('nueHoraFin').value = data.errores.horaAproximada;
        console.log("Errores: ", data);
    }
}) ;

function limpiarCamposCita(camposCita) {
    Object.keys(camposCita).forEach((campo) => {
        const config = camposCita[campo];
        const input = config.input ? document.getElementById(config.input) : null;
        const errorDiv = document.getElementById(config.error);

        if (input) input.classList.remove('is-invalid'); // Quita clase de error
        if (errorDiv) {
            errorDiv.textContent = ''; // Limpia mensaje de error
            errorDiv.style.display = 'none'; // Oculta el contenedor de error
        }
    });
}

function darDeBaja(idCita) {
    document.getElementById('cita_Id').value=idCita;
    document.getElementById('eliminarForm').submit();
}

function filtrarCitas(page=0) {
    const estado = document.getElementById('filtradoDatos').value;
    const fecha = document.getElementById('filtrarFecha').value;
    const params = new URLSearchParams({page});
    if (estado) params.append('estado', estado);
    if (fecha) params.append('fecha', fecha);

    console.log(`Estado: ${estado}, Fecha: ${fecha}`);
    fetch(`/clinicaDental/odontologica/cita/citas?${params.toString()}`)
        .then(response => {
            if (!response.ok) {
                alert("No se encontraron citas con los filtros seleccionados");
            }
            return response.json();
        }).then(data => {
            console.log(data);
            const tabla = document.getElementById('tablaCitas');
            const pagination = document.querySelector('.pagination');
            tabla.innerHTML = '';
            data.datos.forEach(cita => {
               tabla.innerHTML += `
                <tr>
                    <td>${cita.paciente}</td>
                    <td>${cita.fecha}</td>
                    <td>${cita.hora}</td>
                    <td>${cita.horaFin}</td>
                    <td>${cita.motivoCita}</td>
                    <td>${cita.doctor}</td>
                    <td>${cita.comentarios}</td>
                    <td class="d-flex">
                        <button class="btn btn-primary editar-cita" data-bs-toggle="modal"
                        data-bs-target="#modalEdit" onclick=editarCita(${cita.id_Cita})>Editar</button>
                        <button class="btn btn-danger mx-1" onclick=darDeBaja(${cita.id_Cita})>Atendida</button>
                    </td>
                </tr>`;
            });

        pagination.innerHTML = '';
        for (let i = 0; i < data.totalPages; i++) {
            pagination.innerHTML += `
                <li class="page-item ${i === data.currentPage ? 'active' : ''}">
                    <a class="page-link" href="#" onclick="filtrarCitas(${i})">${i + 1}</a>
                </li>`;
        }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}