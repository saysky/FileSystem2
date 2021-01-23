package com.example.client.repostory;

import com.example.client.dto.DocumentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文档数据访问层，DAO层
 * 方法命名规则参考：https://www.cnblogs.com/oxygenG/p/10057525.html
 *
 * @author 言曌
 * @date 2021/1/22 8:48 下午
 */
@Repository
public interface DocumentRepository extends JpaRepository<DocumentDTO, Long> {

    /**
     * 根据用户ID和文档号查询
     *
     * @param userId
     * @param documentKey
     * @return
     */
    List<DocumentDTO> findByUserIdAndDocumentKey(Long userId, String documentKey);

}
