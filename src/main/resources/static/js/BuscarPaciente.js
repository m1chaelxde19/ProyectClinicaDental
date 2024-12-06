    document.getElementById('searchPatientForm').addEventListener('submit', function (event) {
        event.preventDefault(); // Evita la recarga completa del formulario
        const tablaId = document.getElementById('tablaRegister');

        document.getElementById('selectedPatient').textContent = ""; // Limpia el mensaje de paciente seleccionado
        document.getElementById('patientIdInput').value = ""; // Limpia el campo oculto id de paciente
        const searchValue = document.getElementById('searchInput').value;
        const url = `/clinicaDental/odontologica/patients/search?query=${encodeURIComponent(searchValue)}`; // Endpoint para la búsqueda
        fetch(url).then(response => response.json()).then(data => {
                updateModalTable(data,tablaId);
        }).catch(error => {
            console.error('Error al buscar el paciente:', error);
        });
    });

    document.getElementById('searchPatientFormEdit').addEventListener('submit', function (event) {
        event.preventDefault();
        document.getElementById('selecPatient').textContent = "";
        document.getElementById('idPacienteEd').value = "";
        const tablaId=document.getElementById('tableEdit');
        const searchValue = document.getElementById('buscarInput').value;
        const url = `/clinicaDental/odontologica/patients/search?query=${encodeURIComponent(searchValue)}`;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                updateModalTable(data,tablaId);
            })
            .catch(error => {
                console.error('Error al buscar el paciente:', error);
            });
    });

    function updateModalTable(patients,idTabla) {
        idTabla.innerHTML = ''; // Limpia la tabla
        if (patients.length > 0) {
            patients.forEach(patient => {
                const row = `
                <tr>
                    <td>${patient.dto_dni}</td>
                    <td>${patient.dto_nombre}</td>
                    <td>${patient.dto_apellido}</td>
                    <td>${patient.dto_email}</td>
                    <td>${formatofecha(patient.dto_fechaNacimiento)}</td>
                    <td>
                        <button class="btn btn-primary" onclick="selectPatient(${patient.dto_id}, '${patient.dto_nombre}')">Seleccionar</button>
                    </td>
                </tr>
            `;
                idTabla.innerHTML += row;
            });
        } else {
            idTabla.innerHTML = '<tr><td colspan="4">No se encontraron pacientes.</td></tr>';
        }
    }

    function formatofecha(fecha) {
        const fechaxde = new Date(fecha);
        const dia = fechaxde.getDate(); //dia
        const mes = fechaxde.getMonth() + 1; //mes
        const year = fechaxde.getFullYear(); //año
        return `${dia}/${mes}/${year}`;
    }

    function selectPatient(id, name) {
        document.getElementById('selectedPatient').textContent = `Paciente seleccionado: ${name}`;
        document.getElementById('patientIdInput').value = id;
        document.getElementById('selecPatient').textContent = `Paciente seleccionado: ${name}`;
        document.getElementById('idPacienteEd').value = id;
    }

