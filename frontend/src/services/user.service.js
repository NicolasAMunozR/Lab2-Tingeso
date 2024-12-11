import httpClient from "../http-common";

const getUserById = id => {
    return httpClient.get(`/nuevosUsuarios/${id}`);
}

const getAll = () => {
    return httpClient.get("/nuevosUsuarios/all");
}

const getCredits = id => {
    return httpClient.get(`/nuevosUsuarios/credits/${id}`);
}

const create = (formData) => {
    return httpClient.post("/nuevosUsuarios/register", formData, {
      headers: {
        "Content-Type": "multipart/form-data", // Esto es manejado automáticamente
      },
    });
  };

const update = (id, file, email) => {
    return httpClient.put(`/nuevosUsuarios/update/${id}`, {params: {file, email}});
}

const remove = id => {
    return httpClient.delete(`/nuevosUsuarios/delete/${id}`);
}

const simulation = data => {
    return httpClient.post("/simulacionCredito/simulation", data);
}

const save = data => {
    return httpClient.post("/nuevosUsuarios/save", data);
}

const deposit = (formData) => {
    return httpClient.put("/nuevosUsuarios/deposit", formData,{
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });
}

const withdraw = (formData) => {
    return httpClient.put("/nuevosUsuarios/withdrawal", formData,{
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });
}

const get = id => {
    return httpClient.get(`/nuevosUsuarios/users/name/${id}`);
}
export default { get, getAll, getCredits, create, update, remove, simulation, save, deposit, withdraw, getUserById };