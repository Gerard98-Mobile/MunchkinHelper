package gerard.example.munchkinhelper.db.repository

import gerard.example.munchkinhelper.db.dao.SchemeDao
import gerard.example.munchkinhelper.model.Scheme

class SchemeRepository(private val schemeDao: SchemeDao) {

    suspend fun loadSchemes() : List<Scheme>{
        return schemeDao.findAll()
    }

    suspend fun insert(scheme: Scheme){
        schemeDao.insert(scheme)
    }

    suspend fun delete(scheme: Scheme){
        schemeDao.delete(scheme)
    }


}