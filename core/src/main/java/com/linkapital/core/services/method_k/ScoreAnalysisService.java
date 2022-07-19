package com.linkapital.core.services.method_k;

import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.method_k.contract.enums.ScoreType;
import lombok.NonNull;

public interface ScoreAnalysisService {

    void updateResumeScore(@NonNull CompanyUser companyUser, @NonNull ScoreType... scoreTypes);

}
