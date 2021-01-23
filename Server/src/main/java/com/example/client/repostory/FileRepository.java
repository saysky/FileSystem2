package com.example.client.repostory;

import com.example.client.dto.FileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 文件数据访问层，DAO层
 * 方法命名规则参考：https://www.cnblogs.com/oxygenG/p/10057525.html
 *
 * @author 言曌
 * @date 2021/1/22 8:48 下午
 */
@Repository
public interface FileRepository extends JpaRepository<FileDTO, Long> {

}
