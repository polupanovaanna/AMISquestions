package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new ru.fmcs.hse.amisquestions.DataBinderMapperImpl());
  }
}
