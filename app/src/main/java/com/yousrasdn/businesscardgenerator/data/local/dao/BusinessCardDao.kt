package com.yousrasdn.businesscardgenerator.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yousrasdn.businesscardgenerator.data.local.entity.BusinessCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessCardDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(card: BusinessCardEntity): Long
    
    @Update
    suspend fun update(card: BusinessCardEntity)
    
    @Delete
    suspend fun delete(card: BusinessCardEntity)
    
    @Query("SELECT * FROM business_cards WHERE isMyCard = 1 LIMIT 1")
    fun getMyCard(): Flow<BusinessCardEntity?>
    
    @Query("SELECT * FROM business_cards WHERE id = :id")
    suspend fun getCardById(id: Long): BusinessCardEntity?
    
    @Query("SELECT * FROM business_cards ORDER BY createdAt DESC")
    fun getAllCards(): Flow<List<BusinessCardEntity>>
    
    @Query("DELETE FROM business_cards WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM business_cards")
    suspend fun deleteAll()
}
