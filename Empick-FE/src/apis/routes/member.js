export const MemberAPI = {
    REGISTER: '/api/v1/members/registrations',
    FIND_MEMBERS: '/api/v1/members',
    ME: '/api/v1/members/me',
    PROFILE_IMAGE: (memberId) => `/api/v1/members/${memberId}/profile-image`, // 파일을 직접 업로드 하고 파일 경로는 보낼 필요가 없어짐
    ROLE: (employeeNumber) => `/api/v1/members/roles?employeeNumber=${employeeNumber}`,
    MY_ROLE: '/api/v1/members/my-roles',
    FIND_MEMBER_BY_ID: (id) => `/api/v1/members/${id}`,
    EDIT_PROPOSALS: '/api/v1/members/proposals',
    APPROVE_PROPOSAL: (proposalId) => `/api/v1/members/proposals/${proposalId}/approve`,
    REJECT_PROPOSAL: (proposalId) => `/api/v1/members/proposals/${proposalId}/reject`
}; 