package kr.ac.kopo.ctc.kopo01.board.repository;


import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SampleDao2 {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SampleDao2(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Sample> findAll() {
        String sql = "SELECT * FROM sample";
        return jdbcTemplate.query(sql, new SampleRowMapper());
    }

    public Sample findById(Long id) {
        String sql = "SELECT * FROM sample WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new SampleRowMapper(), id);
    }

    private static class SampleRowMapper implements RowMapper<Sample> {
        @Override
        public Sample mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sample sample = new Sample();
            sample.setId(rs.getLong("id"));
            sample.setTitle(rs.getString("title"));
            return sample;
        }
    }
}
